package de.jost_net.JVerein.gui.dialogs;

import java.rmi.RemoteException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.jost_net.JVerein.JVereinPlugin;
import de.jost_net.JVerein.gui.input.SEPALandInput;
import de.jost_net.JVerein.io.IBankverbindung;
import de.jost_net.JVerein.rmi.Bank;
import de.jost_net.JVerein.rmi.SEPAParam;
import de.jost_net.JVerein.util.IBANException;
import de.jost_net.JVerein.util.SEPA;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.dialogs.AbstractDialog;
import de.willuhn.jameica.gui.input.TextInput;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.jameica.system.OperationCanceledException;
import de.willuhn.logging.Logger;

/**
 * Bearbeitung einer Bankverbindung.
 */
public class BankverbindungDialog extends AbstractDialog<IBankverbindung>
{
  private IBankverbindung bankverbindung;

  private SEPALandInput land = null;

  private TextInput blz = null;

  private TextInput konto = null;

  private TextInput bic = null;

  private TextInput iban = null;

  /**
   * @param position
   * @throws RemoteException
   */
  public BankverbindungDialog(int position, IBankverbindung bankverbindung)
      throws RemoteException
  {
    super(position);
    setTitle("Bankverbindung");
    this.bankverbindung = bankverbindung;
    setSize(400, 300);
  }

  @Override
  protected void paint(Composite parent) throws Exception
  {
    LabelGroup groupALT = new LabelGroup(parent, "Bankverbindung -alt-");
    groupALT.addLabelPair("Land", getSEPALand());
    groupALT.addInput(getBLZ());
    groupALT.addLabelPair("Konto", getKonto());

    LabelGroup groupNEU = new LabelGroup(parent, "Bankverbindung -neu-");
    groupNEU.addLabelPair("BIC", getBIC());
    groupNEU.addLabelPair("IBAN", getIBAN());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton(JVereinPlugin.getI18n().tr("�bernehmen"), new Action()
    {
      @Override
      public void handleAction(Object context)
      {
        try
        {
          bankverbindung.setBlz((String) getBLZ().getValue());
          bankverbindung.setKonto((String) getKonto().getValue());
          bankverbindung.setBic((String) getBIC().getValue());
          bankverbindung.setIban((String) getIBAN().getValue());
        }
        catch (RemoteException e)
        {
          e.printStackTrace();
        }
        close();
      }
    }, null, true);
    buttons.addButton(JVereinPlugin.getI18n().tr("abbrechen"), new Action()
    {
      @Override
      public void handleAction(Object context)
      {
        throw new OperationCanceledException();
      }
    });
    buttons.paint(parent);
    // getShell().setMinimumSize(getShell().computeSize(SWT.DEFAULT,
    // SWT.DEFAULT));
  }

  /**
   * @see de.willuhn.jameica.gui.dialogs.AbstractDialog#getData()
   */
  @Override
  public IBankverbindung getData() throws Exception
  {
    return bankverbindung;
  }

  private SEPALandInput getSEPALand() throws RemoteException
  {
    if (land != null)
    {
      return land;
    }
    try
    {
      land = new SEPALandInput();
    }
    catch (IBANException e)
    {
      throw new RemoteException(e.getMessage());
    }
    return land;
  }

  private TextInput getKonto() throws RemoteException
  {
    if (konto != null) // && !konto.getControl().isDisposed())
    {
      return konto;
    }
    konto = new TextInput(bankverbindung.getKonto(), 12);
    konto.addListener(new AltBankListener());
    return konto;
  }

  private TextInput getBLZ() throws RemoteException
  {
    if (blz != null) // && !blz.getControl().isDisposed())
    {
      return blz;
    }
    blz = new TextInput(bankverbindung.getBlz(), 8);
    blz.setName("BLZ");
    blz.addListener(new AltBankListener());
    return blz;
  }

  private TextInput getBIC() throws RemoteException
  {
    if (bic != null)
    {
      return bic;
    }
    bic = new TextInput(bankverbindung.getBic(), 11);
    bic.setName("BIC");
    bic.setEnabled(false);
    return bic;
  }

  private TextInput getIBAN() throws RemoteException
  {
    if (iban != null)
    {
      return iban;
    }
    iban = new TextInput(bankverbindung.getIban(), 23);
    iban.setName("IBAN");
    iban.setEnabled(false);
    return iban;
  }

  private class AltBankListener implements Listener
  {

    @Override
    public void handleEvent(Event event)
    {
      String blzs = null;
      String kontos = null;
      SEPAParam param = null;
      try
      {
        param = (SEPAParam) getSEPALand().getValue();
        blzs = (String) getBLZ().getValue();
        if (blzs.length() > 0)
        {
          Bank b = SEPA.getBankByBLZ(blzs);
          getBIC().setValue(b.getBIC());
        }
      }
      catch (Exception e)
      {
        Logger.error(e.getMessage());
      }
      try
      {
        kontos = (String) getKonto().getValue();
        getIBAN().setValue(SEPA.createIban(kontos, blzs, param.getID()));
      }
      catch (Exception e)
      {
        Logger.error(e.getMessage());
      }

    }
  }

}