package TransferApp;

/**
* TransferApp/UserFilesHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Transfer.idl
* Segunda-feira, 14 de Outubro de 2019 18h44min21s GFT
*/

public final class UserFilesHolder implements org.omg.CORBA.portable.Streamable
{
  public TransferApp.UserFiles value = null;

  public UserFilesHolder ()
  {
  }

  public UserFilesHolder (TransferApp.UserFiles initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = TransferApp.UserFilesHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    TransferApp.UserFilesHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return TransferApp.UserFilesHelper.type ();
  }

}
