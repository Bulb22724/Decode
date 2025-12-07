package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Autonomous
public class DisplayConfig extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        ProcessBuilder process = new ProcessBuilder("su -c 'cat /data/media/0/FIRST/main.xml' root");

        //File configFile = new File("/data/media/0/FIRST/main.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        StringBuilder configbuilder = new StringBuilder();
        /*try {
            process.start();
            process.wait();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(configFile);
            NodeList nodes = document.getElementsByTagName("LynxModule");
            ArrayList<Node> hubs = new ArrayList<Node>();
            for (int i = 0; i < nodes.getLength(); i++) {
                hubs.add(nodes.item(i));
            }

            for (Node hub : hubs) {
                NodeList components = hub.getChildNodes();
                configbuilder.append(String.format("Хаб %s: \n", hub.getNodeName()));
                for (int i = 0; i < components.getLength(); i++) {
                    Node component = components.item(i);
                    configbuilder.append(component.getNodeType());
                    configbuilder.append(String.format("\t$s ($s) на порте $d\n", component.getNodeName(), component.getAttributes().getNamedItem("name"), component.getAttributes().getNamedItem("port")));

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        telemetry.addLine(configbuilder.toString());
        telemetry.update();
        //telemetry.addData("Конфигурация", formatted);
        */
    }
}
