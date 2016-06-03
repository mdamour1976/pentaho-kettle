/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.ui.spoon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.junit.Test;
import org.pentaho.di.ui.spoon.trans.TransGraph;

public class ExpandedContentManagerTest {

  @Test
  public void testIsBrowserVisibleTransGraph() {
    TransGraph transGraphMock = mock( TransGraph.class );
    Control control1 = mock( Control.class );
    Control control2 = mock( Control.class );
    Browser browser = mock( Browser.class );
    Control[] children = new Control[] { control1, control2, browser };
    when( transGraphMock.getChildren() ).thenReturn( children );
    Boolean result = ExpandedContentManager.isVisible( transGraphMock );
    assertFalse( result );
    children = new Control[] { browser, control1, control2 };
    when( transGraphMock.getChildren() ).thenReturn( children );
    result = ExpandedContentManager.isVisible( transGraphMock );
    assertTrue( result );
  }

  @Test
  public void testShowTransformationBrowserh() {
    TransGraph transGraphMock = mock( TransGraph.class );
    Control control1 = mock( Control.class );
    Control control2 = mock( Control.class );
    Browser browser = mock( Browser.class );
    SashForm sash = mock( SashForm.class );
    when( sash.getWeights() ).thenReturn( new int[] { 277, 722 } );
    Composite comp1 = mock( Composite.class );
    Composite comp2 = mock( Composite.class );
    Composite comp3 = mock( Composite.class );
    Composite comp4 = mock( Composite.class );
    when( browser.getParent() ).thenReturn( comp1 );
    when( comp1.getParent() ).thenReturn( comp2 );
    when( comp2.getParent() ).thenReturn( comp3 );
    when( comp3.getParent() ).thenReturn( sash );
    when( comp4.getParent() ).thenReturn( sash );
    Control[] children = new Control[] { control1, control2, browser };
    when( transGraphMock.getChildren() ).thenReturn( children );
    ExpandedContentManager.createExpandedContent( transGraphMock, "" );
  }

  @Test
  public void testGetTransformationBrowserForTransGraph() {
    TransGraph transGraphMock = mock( TransGraph.class );
    Control control1 = mock( Control.class );
    Control control2 = mock( Control.class );
    Browser mockBrowser = mock( Browser.class );
    Control[] children = new Control[] { control1, control2, mockBrowser };
    when( transGraphMock.getChildren() ).thenReturn( children );
    Browser browser = ExpandedContentManager.getExpandedContentForTransGraph( transGraphMock );
    assertNotNull( browser );
    assertEquals( mockBrowser, browser );
  }

  @Test
  public void testHideTransformationBrowser() {
    TransGraph transGraphMock = mock( TransGraph.class );
    Control control1 = mock( Control.class );
    Control control2 = mock( Control.class );
    Browser browser = mock( Browser.class );
    SashForm sash = mock( SashForm.class );
    Composite comp1 = mock( Composite.class );
    Composite comp2 = mock( Composite.class );
    Composite comp3 = mock( Composite.class );
    when( browser.getParent() ).thenReturn( comp1 );
    when( comp1.getParent() ).thenReturn( comp2 );
    when( comp2.getParent() ).thenReturn( comp3 );
    when( comp3.getParent() ).thenReturn( sash );
    Control[] children = new Control[] { control1, control2, browser };
    when( transGraphMock.getChildren() ).thenReturn( children );
    ExpandedContentManager.hideExpandedContent( transGraphMock );
  }
}
