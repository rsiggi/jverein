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
 **********************************************************************/
package de.jost_net.JVerein.gui.menu;

import de.jost_net.JVerein.gui.action.WiedervorlageDeleteAction;
import de.jost_net.JVerein.gui.action.WiedervorlageErledigungAction;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.TablePart;

/**
 * Kontext-Menu zu den Zusatzabbuchungen.
 */
public class WiedervorlageMenu extends ContextMenu
{

  /**
   * Erzeugt ein Kontext-Menu fuer die Wiedervorlagen.
   */
  public WiedervorlageMenu(TablePart table)
  {
    addItem(new CheckedContextMenuItem("Erledigung",
        new WiedervorlageErledigungAction(table)));
    addItem(new CheckedContextMenuItem("L�schen...",
        new WiedervorlageDeleteAction()));
  }
}
