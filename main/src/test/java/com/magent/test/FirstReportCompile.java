package com.magent.test;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

public class FirstReportCompile
{
  public static void main(String[] args)
  {
    try
    {
      System.out.println("Compiling report...");
      JasperReport report=JasperCompileManager.compileReport(Thread.currentThread().getContextClassLoader().getResourceAsStream("FirstReport.jrxml"));
      System.out.println("Done!");
    }
    catch (JRException e)
    {
      e.printStackTrace();
    }
  }
}
