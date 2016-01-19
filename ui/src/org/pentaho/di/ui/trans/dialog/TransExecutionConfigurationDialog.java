/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2013 by Pentaho : http://www.pentaho.com
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

package org.pentaho.di.ui.trans.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.pentaho.di.cluster.SlaveServer;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransExecutionConfiguration;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.ui.ConfigurationDialog;
import org.pentaho.di.ui.core.dialog.ErrorDialog;

public class TransExecutionConfigurationDialog extends ConfigurationDialog {
  private static Class<?> PKG = TransDialog.class; // for i18n purposes, needed by Translator2!!

  public TransExecutionConfigurationDialog( Shell parent, TransExecutionConfiguration configuration,
      TransMeta transMeta ) {
    super( parent, configuration, transMeta );
  }

  protected void localOptionsComposite( Class<?> PKG, String prefix ) {

    Label localDescriptionLabel = new Label( localOptionsComposite, SWT.NONE ); // TODO localize
    props.setLook( localDescriptionLabel );
    localDescriptionLabel.setText( "The transformation will run on the machine you are using." );
    FormData fd_localDescriptionLabel = new FormData();
    fd_localDescriptionLabel.left = new FormAttachment( environmentSeparator, 15 );
    fd_localDescriptionLabel.top = new FormAttachment( 0, 12 );
    localDescriptionLabel.setLayoutData( fd_localDescriptionLabel );
  }

  protected void serverOptionsComposite( Class<?> PKG, String prefix ) {

    wlRemoteHost = new Label( serverOptionsComposite, SWT.NONE );
    props.setLook( wlRemoteHost );
    wlRemoteHost.setText( "Server" ); // BaseMessages.getString( PKG, prefix + ".RemoteHost.Label" ) ); TODO LOCALIZE
    // wlRemoteHost.setToolTipText( BaseMessages.getString( TODO LOCALIZE
    // PKG, prefix + ".RemoteHost.Tooltip" ) );
    FormData fdlRemoteHost = new FormData();
    fdlRemoteHost.top = new FormAttachment( 0, 12 );
    fdlRemoteHost.left = new FormAttachment( environmentSeparator, 15 );
    wlRemoteHost.setLayoutData( fdlRemoteHost );

    wRemoteHost = new CCombo( serverOptionsComposite, SWT.BORDER );
    // wRemoteHost.setToolTipText( BaseMessages.getString( TODO LOCALIZE
    // PKG, prefix + ".RemoteHost.Tooltip" ) );
    props.setLook( wRemoteHost );
    FormData fdRemoteHost = new FormData();
    fdRemoteHost.left = new FormAttachment( wlRemoteHost, 0, SWT.LEFT );
    fdRemoteHost.right = new FormAttachment( 100, -293 );
    fdRemoteHost.top = new FormAttachment( wlRemoteHost, 6 );
    wRemoteHost.setLayoutData( fdRemoteHost );
    for ( int i = 0; i < abstractMeta.getSlaveServers().size(); i++ ) {
      SlaveServer slaveServer = abstractMeta.getSlaveServers().get( i );
      wRemoteHost.add( slaveServer.toString() );
    }

    wPassExport = new Button( serverOptionsComposite, SWT.CHECK );
    wPassExport.setText( "Send resources to this server" ); // BaseMessages.getString( PKG, prefix + ".PassExport.Label"
                                                            // ) ); TODO LOCALIZE
    // wPassExport.setToolTipText( BaseMessages.getString( TODO LOCALIZE
    // PKG, prefix + ".PassExport.Tooltip" ) );
    props.setLook( wPassExport );
    FormData fdPassExport = new FormData();
    fdPassExport.left = new FormAttachment( wRemoteHost, 0, SWT.LEFT );
    fdPassExport.top = new FormAttachment( wRemoteHost, 6 );
    wPassExport.setLayoutData( fdPassExport );

  }

  protected void clusteredOptionsComposite( Class<?> PKG, String prefix ) {

    Label clusterDescriptionLabel = new Label( clusteredOptionsComposite, SWT.NONE );
    props.setLook( clusterDescriptionLabel );
    clusterDescriptionLabel.setText(
        "This option is available because you have clusters set on steps in this transformation." ); // TODO LOCALIZE
    FormData fd_clusterDescriptionLabel = new FormData();
    fd_clusterDescriptionLabel.top = new FormAttachment( 0, 12 );
    fd_clusterDescriptionLabel.left = new FormAttachment( environmentSeparator, 15 );
    clusterDescriptionLabel.setLayoutData( fd_clusterDescriptionLabel );

    showTransformationsCheckbox = new Button( clusteredOptionsComposite, SWT.CHECK );
    props.setLook( showTransformationsCheckbox );
    FormData fd_resroucesCheckBox = new FormData();
    fd_resroucesCheckBox.top = new FormAttachment( clusterDescriptionLabel, 10 );
    fd_resroucesCheckBox.left = new FormAttachment( clusterDescriptionLabel, 0, SWT.LEFT );
    showTransformationsCheckbox.setLayoutData( fd_resroucesCheckBox );
    showTransformationsCheckbox.setText( "Show transformations" ); // TODO LOCALIZE
  }

  protected void optionsSectionControls() {

    wSafeMode = new Button( gDetails, SWT.CHECK );
    wSafeMode.setText( BaseMessages.getString( PKG, "TransExecutionConfigurationDialog.SafeMode.Label" ) );
    wSafeMode.setToolTipText( BaseMessages.getString( PKG, "TransExecutionConfigurationDialog.SafeMode.Tooltip" ) );
    props.setLook( wSafeMode );
    FormData fdSafeMode = new FormData();
    fdSafeMode.right = new FormAttachment( 0, 186 );
    fdSafeMode.top = new FormAttachment( 0, 40 );
    fdSafeMode.left = new FormAttachment( 0, 10 );
    wSafeMode.setLayoutData( fdSafeMode );

    wGatherMetrics = new Button( gDetails, SWT.CHECK );
    wGatherMetrics.setText( BaseMessages.getString( PKG, "TransExecutionConfigurationDialog.GatherMetrics.Label" ) );
    wGatherMetrics.setToolTipText( BaseMessages.getString( PKG,
        "TransExecutionConfigurationDialog.GatherMetrics.Tooltip" ) );
    props.setLook( wGatherMetrics );
    FormData fdGatherMetrics = new FormData();
    fdGatherMetrics.right = new FormAttachment( 0, 186 );
    fdGatherMetrics.top = new FormAttachment( 0, 65 );
    fdGatherMetrics.left = new FormAttachment( 0, 10 );
    wGatherMetrics.setLayoutData( fdGatherMetrics );

    wClearLog = new Button( gDetails, SWT.CHECK );
    wClearLog.setText( "Clear log before running" ); // TODO Localize BaseMessages.getString( PKG,
                                                     // "TransExecutionConfigurationDialog.ClearLog.Label" ) );
    wClearLog.setToolTipText( BaseMessages.getString( PKG, "TransExecutionConfigurationDialog.ClearLog.Tooltip" ) );
    props.setLook( wClearLog );
    FormData fdClearLog = new FormData();
    fdClearLog.right = new FormAttachment( 0, 171 );
    fdClearLog.top = new FormAttachment( 0, 15 );
    fdClearLog.left = new FormAttachment( 0, 10 );
    wClearLog.setLayoutData( fdClearLog );

    wlLogLevel = new Label( gDetails, SWT.RIGHT );
    props.setLook( wlLogLevel );
    wlLogLevel.setText( BaseMessages.getString( PKG, "TransExecutionConfigurationDialog.LogLevel.Label" ) );
    wlLogLevel.setToolTipText( BaseMessages.getString( PKG, "TransExecutionConfigurationDialog.LogLevel.Tooltip" ) );
    FormData fdlLogLevel = new FormData();
    fdlLogLevel.right = new FormAttachment( 0, 290 );
    fdlLogLevel.top = new FormAttachment( 0, 17 );
    fdlLogLevel.left = new FormAttachment( 0, 240 );
    wlLogLevel.setLayoutData( fdlLogLevel );

    wLogLevel = new CCombo( gDetails, SWT.READ_ONLY | SWT.BORDER );
    wLogLevel.setToolTipText( BaseMessages.getString( PKG, "TransExecutionConfigurationDialog.LogLevel.Tooltip" ) );
    props.setLook( wLogLevel );
    FormData fdLogLevel = new FormData();
    fdLogLevel.left = new FormAttachment( wlLogLevel, 6 );
    fdLogLevel.width = 200;
    fdLogLevel.top = new FormAttachment( wClearLog, -2, SWT.TOP );
    fdLogLevel.right = new FormAttachment( 0, 446 );
    wLogLevel.setLayoutData( fdLogLevel );
    wLogLevel.setItems( LogLevel.getLogLevelDescriptions() );
  }

  public boolean open() {

    mainLayout( PKG, "TransExecutionConfigurationDialog" );

    environmentTypeSectionLayout( PKG, "TransExecutionConfigurationDialog" );
    optionsSectionLayout();
    parametersSectionLayout( PKG, "TransExecutionConfigurationDialog" );
    buttonsSectionLayout( PKG, "TransExecutionConfigurationDialog" );

    getData();
    openDialog();
    return retval;
  }

  private void getVariablesData() {
    wVariables.clearAll( false );
    List<String> variableNames = new ArrayList<String>( configuration.getVariables().keySet() );
    Collections.sort( variableNames );

    for ( int i = 0; i < variableNames.size(); i++ ) {
      String variableName = variableNames.get( i );
      String variableValue = configuration.getVariables().get( variableName );

      if ( Const.indexOfString( variableName, abstractMeta.listParameters() ) < 0 ) {

        TableItem tableItem = new TableItem( wVariables.table, SWT.NONE );
        tableItem.setText( 1, variableName );
        tableItem.setText( 2, Const.NVL( variableValue, "" ) );
      }
    }
    wVariables.removeEmptyRows();
    wVariables.setRowNums();
    wVariables.optWidth( true );
  }

  public void getData() {

    wExecLocal.setSelection( configuration.isExecutingLocally() );
    if ( configuration.isExecutingLocally() ) {
      stackedLayout.topControl = localOptionsComposite;
    }
    wExecRemote.setSelection( configuration.isExecutingRemotely() );
    if ( configuration.isExecutingRemotely() ) {
      stackedLayout.topControl = serverOptionsComposite;
    }
    wExecCluster.setSelection( getConfiguration().isExecutingClustered() );
    if ( getConfiguration().isExecutingClustered() ) {
      stackedLayout.topControl = clusteredOptionsComposite;
    }

    wSafeMode.setSelection( configuration.isSafeModeEnabled() );
    wClearLog.setSelection( configuration.isClearingLog() );
    wRemoteHost.setText( configuration.getRemoteServer() == null ? "" : configuration.getRemoteServer().toString() );
    wPassExport.setSelection( configuration.isPassingExport() );
    wGatherMetrics.setSelection( configuration.isGatheringMetrics() );
    showTransformationsCheckbox.setSelection( getConfiguration().isClusterShowingTransformation() );

    wLogLevel.select( configuration.getLogLevel().getLevel() );
    getParamsData();
    getVariablesData();
  }

  public void getInfo() {
    try {
      configuration.setReplayDate( null );
      configuration.setExecutingLocally( wExecLocal.getSelection() );
      configuration.setExecutingRemotely( wExecRemote.getSelection() );
      getConfiguration().setExecutingClustered( wExecCluster.getSelection() );

      // Local data
      // --> preview handled in debug transformation meta dialog

      // Remote data
      if ( wExecRemote.getSelection() ) {
        String serverName = wRemoteHost.getText();
        configuration.setRemoteServer( abstractMeta.findSlaveServer( serverName ) );
        configuration.setPassingExport( wPassExport.getSelection() );
      }
      if ( wExecCluster.getSelection() ) {
        getConfiguration().setClusterShowingTransformation( showTransformationsCheckbox.getSelection() );
      }

      // Clustering data
      getConfiguration().setClusterPosting( wExecCluster.getSelection() ? true : false );
      getConfiguration().setClusterPreparing( wExecCluster.getSelection() ? true : false );
      getConfiguration().setClusterStarting( wExecCluster.getSelection() ? true : false );
      getConfiguration().setClusterShowingTransformation( wExecCluster.getSelection() ? true : false );

      configuration.setSafeModeEnabled( wSafeMode.getSelection() );
      configuration.setClearingLog( wClearLog.getSelection() );
      configuration.setLogLevel( LogLevel.values()[wLogLevel.getSelectionIndex()] );
      configuration.setGatheringMetrics( wGatherMetrics.getSelection() );

      // The lower part of the dialog...
      getInfoParameters();
      getInfoVariables();
    } catch ( Exception e ) {
      new ErrorDialog( shell, "Error in settings", "There is an error in the dialog settings", e );
    }
  }

  /**
   * @return the configuration
   */
  public TransExecutionConfiguration getConfiguration() {
    return (TransExecutionConfiguration) configuration;
  }
}
