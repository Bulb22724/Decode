package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.File;
import java.util.ArrayList;
import java.lang.Process;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Autonomous
public class DisplayConfig extends LinearOpMode {

    // команда, которая считает /data/media/0/FIRST/main.xml от имени root
    private static final String READ_AS_ROOT_COMMAND = "su -c 'cat /data/media/0/FIRST/main.xml' root";

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        ProcessBuilder processbuilder = new ProcessBuilder(READ_AS_ROOT_COMMAND);

        //File configFile = new File("/data/media/0/FIRST/main.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        StringBuilder configbuilder = new StringBuilder();
        try {
            Process process = processbuilder.start();
            int returncode = process.waitFor();
            // если команда выдала ошибку, код будет ненулевой
            if (returncode != 0) {
                throw new Exception(String.format("Команда \"%s\" выдала ненулевой код ошибки %d", READ_AS_ROOT_COMMAND, returncode));
            }

            InputStream stream = process.getInputStream();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(stream);
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
                    configbuilder.append(String.format("\t$s ($s) на порте $d\n", component.getNodeName(), component.getAttributes().getNamedItem("name"), component.getAttributes().getNamedItem("port")));

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        telemetry.addLine(configbuilder.toString());
        telemetry.update();
    }
}
