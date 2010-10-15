/**********************************************************************
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright (c) by Heiner Jostkleigrewe 
 * basiert auf dem KontoAuswahlDialog aus Hibiscus
 * All rights reserved
 * heiner@jverein.de
 * www.jverein.de
 * $Log$
 * Revision 1.5  2010-10-10 06:37:09  jost
 * Bugfix "leere Kontoauswahl".
 *
 * Revision 1.4  2010/06/09 18:50:05  jost
 * Gr��e des Dialog ver�ndert.
 *
 * Revision 1.3  2009/06/20 12:33:40  jost
 * Vereinheitlichung der Bezeichner
 *
 * Revision 1.2  2009/06/11 21:02:41  jost
 * Vorbereitung I18N
 *
 * Revision 1.1  2008/05/22 06:49:47  jost
 * Buchführung
 *
 **********************************************************************/

package de.jost_net.JVerein.gui.dialogs;

import org.eclipse.swt.widgets.Composite;

import de.jost_net.JVerein.JVereinPlugin;
import de.jost_net.JVerein.gui.parts.KontoList;
import de.jost_net.JVerein.rmi.Konto;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.dialogs.AbstractDialog;
import de.willuhn.jameica.gui.util.ButtonArea;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.jameica.system.OperationCanceledException;

/**
 * Ein Dialog, ueber den man ein Konto auswaehlen kann.
 */
public class KontoAuswahlDialog extends AbstractDialog
{
  private String text = null;

  private Konto choosen = null;

  private boolean keinkonto;

  public KontoAuswahlDialog(int position, boolean keinkonto)
  {
    super(position);
    super.setSize(400, 300);
    this.setTitle(JVereinPlugin.getI18n().tr("Konto-Auswahl"));
    this.keinkonto = keinkonto;
  }

  @Override
  protected void paint(Composite parent) throws Exception
  {
    LabelGroup group = new LabelGroup(parent, JVereinPlugin.getI18n().tr(
        "Verf�gbare Konten"));

    if (text == null || text.length() == 0)
    {
      text = JVereinPlugin.getI18n().tr(
          "Bitte w�hlen Sie das gew�nschte Konto aus.");
    }
    group.addText(text, true);

    Action a = new Action()
    {
      public void handleAction(Object context) 
      {
        // wenn kein Konto ausgew�hlt sein darf, wird null zur�ckgegeben.
        if (context == null && keinkonto)
        {
          choosen = null;
          return;
        }
        if (context == null || !(context instanceof Konto))
        {
          return;
        }
        choosen = (Konto) context;
        close();
      }
    };
    final KontoList konten = new de.jost_net.JVerein.gui.parts.KontoList(a);
    konten.setContextMenu(null);
    konten.setMulti(false);
    konten.setSummary(false);
    konten.paint(parent);

    ButtonArea b = new ButtonArea(parent, 3);
    b.addButton(i18n.tr(JVereinPlugin.getI18n().tr("�bernehmen")), new Action()
    {
      public void handleAction(Object context) 
      {
        Object o = konten.getSelection();
        if (o == null || !(o instanceof Konto))
          return;

        choosen = (Konto) o;
        close();
      }
    });
    if (keinkonto)
    {
      b.addButton(i18n.tr("kein Konto"), new Action()
      {
        public void handleAction(Object context) 
        {
          choosen = null;
          close();
        }
      });
    }
    b.addButton(i18n.tr("abbrechen"), new Action()
    {
      public void handleAction(Object context) 
      {
        throw new OperationCanceledException();
      }
    });
  }

  /**
   * Liefert das ausgewaehlte Konto zurueck oder <code>null</code> wenn der
   * Abbrechen-Knopf gedrueckt wurde.
   * 
   * @see de.willuhn.jameica.gui.dialogs.AbstractDialog#getData()
   */
  @Override
  protected Object getData() throws Exception
  {
    return choosen;
  }

  /**
   * Optionale Angabe des anzuzeigenden Textes. Wird hier kein Wert gesetzt,
   * wird ein Standard-Text angezeigt.
   * 
   * @param text
   */
  public void setText(String text)
  {
    this.text = text;
  }

}
