package snmp;

import org.snmp4j.*;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.security.Priv3DES;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.smi.*;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;
import snmp.entities.DeviceHolder;
import snmp.entities.OidHolder;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


public class SnmpManager implements CommandResponder {
    private static final Set<OidHolder> staticOidHolder;
    private static final Set<OidHolder> dynamicOidHolder;
    private static final Set<DeviceHolder> deviceHolder;
    private static final int ALIVE = 1;
    private static final int DEAD = 0;
    private static Map<String, Integer> deviceStatus;
    private static DeviceHolder currentDevice;

    static {
        // OidHolder Set<OidHolder>
        // 0->oidValue
        // 1->trapValue
        staticOidHolder = FileManager.getOidInfo();
        dynamicOidHolder = FileManager.getDynamicOidInfo();

        //DeviceHolder Set<DeviceHolder>
        // 0->id
        // 1->deviceName
        // 2->ipAddress
        // 3->lifeCheckOid
        deviceHolder = FileManager.getDeviceInfo();
    }

    // periodical check
    // device oids are queried
    // if responds its alive
    private void periodicalCheckDevices() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    for (DeviceHolder device : deviceHolder) {
                        currentDevice = device;
                        OidManager.getV2OidValue(device.getIpAdress(), device.getLifeCheckOid());

                        // DO Something...

                        deviceStatus.put(device.getId(), ALIVE);
                        System.out.println("I am alive and fine...");
                    }

                } catch (NullPointerException e) {
                    System.out.println("Exceptione girdik");
                    deviceStatus.put(currentDevice.getId(), DEAD);
                    e.printStackTrace();
                }
            }
        });

        thread.setName("periodicalCheckThread");
        thread.start();
    }

    //check the oid equals to the other oid
    private void staticOid(Map<String, String> map, String oid, String value) {
        if (map.get(oid).equals(value)) {
            //intrusionMessage
            //eventMessage
            // DO SOMETHING
        }
    }

    // check the change in the oid
    private void dynamicOidIncrement(Map<String, String> map, String oid, String value) {
        if (!map.get(oid).equals(value)) {
            // intrusionMessage
            // eventMessage
        }
    }

    public void connectSNMP(String ipAddress) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipAddress);
        sb.append("/162"); // default trap port
        try {
            listen(new UdpAddress(sb.toString()));
        } catch (Exception e) {
            System.out.println("Can not connect the " + sb.toString());
            e.printStackTrace();
        }
    }

    public void connectSNMP(String ipAddress, String port) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(ipAddress);
            sb.append("/");
            sb.append(port);

            listen(new UdpAddress(sb.toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void listen(TransportIpAddress address)
            throws IOException {
        AbstractTransportMapping transport;
        if (address instanceof TcpAddress) {
            transport = new DefaultTcpTransportMapping((TcpAddress) address);
        } else {
            transport = new DefaultUdpTransportMapping((UdpAddress) address);
        }

        ThreadPool threadPool = ThreadPool.create("DispatcherPool", 10);
        MessageDispatcher mtDispatcher = new MultiThreadedMessageDispatcher(
                threadPool, new MessageDispatcherImpl());

        // add message processing models
        mtDispatcher.addMessageProcessingModel(new MPv1());
        mtDispatcher.addMessageProcessingModel(new MPv2c());

        // add all security protocols
        SecurityProtocols.getInstance().addDefaultProtocols();
        SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());

        // Create Target
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));

        Snmp snmp = new Snmp(mtDispatcher, transport);
        snmp.addCommandResponder(this);

        transport.listen();
        System.out.println("Listening on " + address);
        // .1.3.6.1.2.1.1.3
        try {
            this.wait();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    // PDU has getVariableBindings method Vector<? extends VariableBinding>
    // VariableBinding has getOid getVariable methods.. OID || Variable
    public void processPdu(CommandResponderEvent cmdRespEvent) {
//        System.out.println("Received PDU...");
        PDU pdu = cmdRespEvent.getPDU();
        if (pdu != null) {

//             info message
//            System.out.println("Trap Type = " + pdu.getType());
//            System.out.println("Variable Bindings = "
//                    + pdu.getVariableBindings());
//                    + pdu.getVariableBindings()
//            );

            Vector<? extends VariableBinding> variables = pdu.getVariableBindings();
            String oid = null;
            String value = null;
            for (VariableBinding variable : variables) {
                oid = variable.getOid().toString();
                value = variable.getVariable().toString();
                checkStaticOid(oid, value, staticOidHolder);
                checkDynamicOid(oid, value, dynamicOidHolder);
            }
        }
    }

    private void checkStaticOid(String oid, String value, Set<OidHolder> holder) {
        for (OidHolder oidHolder : holder) {
            if (oidHolder.getOidValue().equals(oid) && oidHolder.getTrap().equals(value)) {
                System.out.println("Static Match Sağlandı");
            }
        }
    }

    private void checkDynamicOid(String oid, String value, Set<OidHolder> holder) {
        for (OidHolder oidHolder : holder) {
            if (oidHolder.getOidValue().equals(oid) && !oidHolder.getTrap().equals(value)) {
                System.out.println("Dinamik Match Sağlandı");
            }
        }
    }

    // Name/OID: .1.3.6.1.4.1.25.1.4.3.10.3.0; Value (Integer): 1


}
