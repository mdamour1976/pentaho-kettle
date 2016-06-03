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

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;
import org.pentaho.di.ui.spoon.trans.TransGraph;

public final class ExpandedContentManager {

  private static int[] savedSashWeights;

  /**
   * isBrowserVisible
   * 
   * @return a boolean that represents that the web browser is the topmost control of the active TransGraph. If
   *         browser hasn't been created it will return false.
   */
  public static boolean isVisible() {
    return isVisible( Spoon.getInstance().getActiveTransGraph() );
  }

  /**
   * isBrowserVisible( TransGraph graph )
   * 
   * @param graph
   *          a TransGraph object that is being interrogated to see if the web browser is the topmost control
   * @return true if the web browser is the topmost control of the graph
   */
  public static boolean isVisible( TransGraph graph ) {
    return graph.getChildren()[0] instanceof Browser;
  }  
  
  /**
   * createExpandedContent
   * 
   * creates a web browser for the current TransGraph
   */
  public static void createExpandedContent( String url ) {
    createExpandedContent( Spoon.getInstance().getActiveTransGraph(), url );
  }

  /**
   * createExpandedContent( TransGraph parent )
   * 
   * Create a web browser for the TransGraph argument.
   * 
   * @param parent
   *          a TransGraph that will be the parent of the web browser.
   * @param url
   *          The content to open and expand
   */
  public static void createExpandedContent( TransGraph parent, String url ) {
    if ( parent == null ) {
      return;
    }
    Browser browser = getExpandedContentForTransGraph( parent );
    if ( browser == null ) {
      browser = new Browser( parent, SWT.NONE );
    }
    browser.setUrl( url );
  }

  /**
   * showTransformationBrowser
   * 
   * Creates and shows the web browser for the active TransGraph
   */
  public static void showExpandedContent() {
    showExpandedContent( Spoon.getInstance().getActiveTransGraph() );
  }

  /**
   * showExpandedContent( TransGraph graph )
   * 
   * @param graph
   *          TransGraph to create the web browser for. If the wev browser hasn't been created this will create one.
   *          Else it will just bring the web browser associated to this TransGraph to the top.
   */
  public static void showExpandedContent( TransGraph graph ) {
    if ( graph == null ) {
      return;
    }
    Browser browser = getExpandedContentForTransGraph( graph );
    if ( browser == null ) {
      return;
    }
    if ( !isVisible( graph ) ) {
      maximizeExpandedContent( browser );
    }
    browser.moveAbove( null );
    browser.getParent().layout( true );
    browser.getParent().redraw();
  }

  /**
   * getExpandedContentForTransGraph
   * 
   * @param graph
   *          a TransGraph object that will be interrogated for a web browser
   * @return a web browser that is associated with the TransGraph or null if it has yet to be created.
   */
  public static Browser getExpandedContentForTransGraph( TransGraph graph ) {
    for ( Control control : graph.getChildren() ) {
      if ( control instanceof Browser ) {
        return (Browser) control;
      }
    }
    return null;
  }

  /**
   * hideExpandedContent
   * 
   * hides the web browser associated with the active TransGraph
   */
  public static void hideExpandedContent() {
    hideExpandedContent( Spoon.getInstance().getActiveTransGraph() );
  }

  /**
   * hideExpandedContent( TransGraph graph )
   * 
   * @param graph
   *          the TransGraph whose web browser will be hidden
   */
  public static void hideExpandedContent( TransGraph graph ) {
    Browser browser = getExpandedContentForTransGraph( graph );
    if ( browser == null ) {
      return;
    }
    SashForm sash = (SashForm) browser.getParent().getParent().getParent().getParent();
    sash.setWeights( savedSashWeights );

    browser.moveBelow( null );
    browser.getParent().layout( true );
    browser.getParent().redraw();

    sash.layout( true );
    sash.redraw();
  }

  /**
   * maximizeBrowser
   * 
   * @param browser
   *          the browser object to maximize. We try to take up as much of the Spoon window as possible.
   */
  private static void maximizeExpandedContent( Browser browser ) {
    SashForm sash = (SashForm) browser.getParent().getParent().getParent().getParent();
    int[] weights = sash.getWeights();
    savedSashWeights = new int[weights.length];
    System.arraycopy( weights, 0, savedSashWeights, 0, weights.length );
    weights[0] = 0;
    weights[1] = 1000;
    sash.setWeights( weights );
    FormData formData = new FormData();
    formData.top = new FormAttachment( 0, 0 );
    formData.left = new FormAttachment( 0, 0 );
    formData.bottom = new FormAttachment( 100, 0 );
    formData.right = new FormAttachment( 100, 0 );
    browser.setLayoutData( formData );
    browser.getParent().layout( true );
    browser.getParent().redraw();
    sash.layout( true );
    sash.redraw();
  }
}
