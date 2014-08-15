package org.finra.datagenerator.csp;

import org.finra.datagenerator.consumer.DataConsumer;
import org.finra.datagenerator.csp.parse.CSPParser;
import org.finra.datagenerator.writer.DefaultWriter;

import java.io.StringReader;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: K24364 Marshall Peters
 * Date: 8/13/14
 */
public class Test {

    public static String eightLonelyRooks = "@1 {a,b,c,d,e,f,g,h} [T] " +
            "@2 {a,b,c,d,e,f,g,h} [~= 1 2] " +
            "@3 {a,b,c,d,e,f,g,h} [&& ~= 3 2 ~= 3 1] " +
            "@4 {a,b,c,d,e,f,g,h} [&& ~= 4 3 && ~= 4 2 ~= 4 1] " +
            "@5 {a,b,c,d,e,f,g,h} [&& ~= 5 4 && ~= 5 3 && ~= 5 2 ~= 5 1] " +
            "@6 {a,b,c,d,e,f,g,h} [&& ~= 6 5 && ~= 6 4 && ~= 6 3 && ~= 6 2 ~= 6 1] " +
            "@7 {a,b,c,d,e,f,g,h} [&& ~= 7 6 && ~= 7 5 && ~= 7 4 && ~= 7 3 && ~= 7 2 ~= 7 1] " +
            "@8 {a,b,c,d,e,f,g,h} [&& ~= 8 7 && ~= 8 6 && ~= 8 5 && ~= 8 4 && ~= 8 3 && ~= 8 2 ~= 8 1] " +
            "@END";

    public static void main(String args[]) {
        StringReader reader = new StringReader(eightLonelyRooks);
        CSPParser parse = new CSPParser(reader);
        ConstraintSatisfactionProblem csp = parse.parse();

        System.out.println(csp.toString() + "\n\n");

        CSPExecutor exec = new CSPExecutor(csp);
        List<PartialSolution> bootStrap = exec.BFSplit(8);

        System.out.println(bootStrap.toString() + "\n\n");

        CSPDistributor dist = new CSPDistributor();
        dist.setCsp(csp);
        dist.setMaxNumberOfLines(45000);
        dist.setThreadCount(8);

        DataConsumer consumer = new DataConsumer();
        consumer.addDataWriter(new DefaultWriter(System.out, new String[]{"1", "2", "3", "4", "5", "6", "7", "8"}));
        dist.setUserDataOutput(consumer);

        dist.distribute(bootStrap);
    }
}
