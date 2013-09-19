/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2009 Pentaho Corporation.  All rights reserved.
 */

package org.pentaho.reporting.engine.classic.extensions.datasources.olap4j;

import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.ReportDataFactoryException;
import org.pentaho.reporting.engine.classic.core.testsupport.DataSourceTestBase;
import org.pentaho.reporting.engine.classic.extensions.datasources.olap4j.connections.JndiConnectionProvider;

public class LegacyBandedOlap4JJndiTest extends DataSourceTestBase
{
  private static final String[][] QUERIES_AND_RESULTS = Olap4JTestUtil.createQueryArray("-legacy");

  public LegacyBandedOlap4JJndiTest()
  {
  }

  public LegacyBandedOlap4JJndiTest(final String s)
  {
    super(s);
  }


  public void testSaveAndLoad() throws Exception
  {
    runSaveAndLoad(QUERIES_AND_RESULTS);
  }

  public void testDerive() throws Exception
  {
    runDerive(QUERIES_AND_RESULTS);
  }

  public void testSerialize() throws Exception
  {
    runSerialize(QUERIES_AND_RESULTS);
  }

  public void testQuery() throws Exception
  {
    runTest(QUERIES_AND_RESULTS);
  }

  protected DataFactory createDataFactory(final String query) throws ReportDataFactoryException
  {
    final JndiConnectionProvider provider = new JndiConnectionProvider();
    provider.setConnectionPath("SampleOlap4J");

    final LegacyBandedMDXDataFactory dataFactory = new LegacyBandedMDXDataFactory(provider);
    dataFactory.setQuery("default", query, null, null);
    initializeDataFactory(dataFactory);
    return dataFactory;
  }


  public static void _main(final String[] args) throws Exception
  {
    final LegacyBandedOlap4JJndiTest test = new LegacyBandedOlap4JJndiTest();
    test.setUp();
    test.runGenerate(QUERIES_AND_RESULTS);
  }

}
