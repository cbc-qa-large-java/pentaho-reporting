package org.pentaho.reporting.engine.classic.extensions.datasources.pmd;

import junit.framework.TestCase;
import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.JndiConnectionProvider;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.SQLReportDataFactory;
import org.pentaho.reporting.engine.classic.core.modules.parser.bundle.writer.BundleWriter;
import org.pentaho.reporting.libraries.base.util.MemoryByteArrayOutputStream;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

public class PmdDataFactoryWriteTest extends TestCase
{
  public PmdDataFactoryWriteTest()
  {
  }

  protected void setUp() throws Exception
  {
    ClassicEngineBoot.getInstance().start();
  }


  protected MasterReport postProcess(final MasterReport originalReport) throws Exception
  {
    final MemoryByteArrayOutputStream bout = new MemoryByteArrayOutputStream();
    BundleWriter.writeReportToZipStream(originalReport, bout);
    assertTrue(bout.getLength() > 0);

    final ResourceManager mgr = new ResourceManager();
    mgr.registerDefaults();
    final Resource reportRes = mgr.createDirectly(bout.toByteArray(), MasterReport.class);
    return (MasterReport) reportRes.getResource();
  }

  public void testWrite() throws Exception
  {
    final PmdDataFactory df = new PmdDataFactory();
    df.setGlobalScript("GlobalScript");
    df.setGlobalScriptLanguage("GlobalScriptLanguage");
    df.setQuery("QueryName", "QueryText", "ScriptLanguage", "Script");
    final MasterReport report = new MasterReport();
    report.setDataFactory(df);

    final MasterReport masterReport = postProcess(report);
    final PmdDataFactory dataFactory = (PmdDataFactory) masterReport.getDataFactory();
    assertEquals("QueryName", dataFactory.getQueryNames()[0]);
    assertEquals("QueryText", dataFactory.getQuery("QueryName"));
    assertEquals("ScriptLanguage", dataFactory.getScriptingLanguage("QueryName"));
    assertEquals("Script", dataFactory.getScript("QueryName"));
    assertEquals("GlobalScript", dataFactory.getGlobalScript());
    assertEquals("GlobalScriptLanguage", dataFactory.getGlobalScriptLanguage());
  }
}
