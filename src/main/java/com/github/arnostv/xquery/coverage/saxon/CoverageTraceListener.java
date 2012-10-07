package com.github.arnostv.xquery.coverage.saxon;

import net.sf.saxon.Controller;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.TraceListener;
import net.sf.saxon.om.Item;
import net.sf.saxon.trace.InstructionInfo;

import java.io.PrintStream;

public class CoverageTraceListener implements TraceListener {
    int counter;

    private PrintStream out = System.err;

    private final CoverageCollector coverageCollector;

    public CoverageTraceListener(CoverageCollector coverageCollector) {
        this.coverageCollector = coverageCollector;
    }

    @Override
    public void setOutputDestination(PrintStream printStream) {
//        System.out.println("Output is " + printStream);
//        this.out = printStream;
    }

    @Override
    public void open(Controller controller) {
        counter++;
        out.println("Opening " + controller);
    }

    @Override
    public void close() {
        counter++;
        out.flush();
    }

    @Override
    public void enter(InstructionInfo instructionInfo, XPathContext xPathContext) {
        counter++;
        CoverageEvent event = new CoverageEvent(instructionInfo.getSystemId(), instructionInfo.getObjectName().getDisplayName(),instructionInfo.getLineNumber());
        coverageCollector.register(event);
        out.println("Entering " + instructionInfo + "  $$$ " + instructionInfo.getObjectName().getDisplayName() + " * " + instructionInfo.getLineNumber()+"/"+instructionInfo.getColumnNumber()+" @ " + instructionInfo.getSystemId() +  " " + instructionInfo.getClass() + "   #" + counter);
    }

    @Override
    public void leave(InstructionInfo instructionInfo) {
        counter++;
        out.println("Leaving " + instructionInfo + "  " + instructionInfo.getLineNumber()+"/"+instructionInfo.getColumnNumber()+" @ " + instructionInfo.getSystemId() + "   #" + counter);
    }

    @Override
    public void startCurrentItem(Item item) {
        counter++;
        out.println("Start " + item);
    }

    @Override
    public void endCurrentItem(Item item) {
        counter++;
        out.println("End " + item);
    }
}