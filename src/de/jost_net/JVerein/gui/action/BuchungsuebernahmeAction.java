/**********************************************************************
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright (c) by Heiner Jostkleigrewe
 * All rights reserved
 * heiner@jverein.de
 * www.jverein.de
 * $Log$
 * Revision 1.4  2008-03-16 07:35:25  jost
 * Reaktivierung Buchführung
 *
 * Revision 1.2  2007/02/23 20:25:42  jost
 * Mail- und Webadresse im Header korrigiert.
 *
 * Revision 1.1  2006/09/20 15:38:12  jost
 * *** empty log message ***
 *
 **********************************************************************/
package de.jost_net.JVerein.gui.action;

import de.jost_net.JVerein.gui.view.BuchungsUebernahmeView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;

public class BuchungsuebernahmeAction implements Action
{

  public void handleAction(Object context)
  {
    GUI.startView(BuchungsUebernahmeView.class.getName(), null);
  }

}
