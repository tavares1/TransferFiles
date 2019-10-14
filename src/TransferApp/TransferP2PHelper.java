package TransferApp;


/**
* TransferApp/TransferP2PHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Transfer.idl
* Segunda-feira, 14 de Outubro de 2019 18h44min21s GFT
*/

abstract public class TransferP2PHelper
{
  private static String  _id = "IDL:TransferApp/TransferP2P:1.0";

  public static void insert (org.omg.CORBA.Any a, TransferApp.TransferP2P that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static TransferApp.TransferP2P extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (TransferApp.TransferP2PHelper.id (), "TransferP2P");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static TransferApp.TransferP2P read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_TransferP2PStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, TransferApp.TransferP2P value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static TransferApp.TransferP2P narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof TransferApp.TransferP2P)
      return (TransferApp.TransferP2P)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      TransferApp._TransferP2PStub stub = new TransferApp._TransferP2PStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static TransferApp.TransferP2P unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof TransferApp.TransferP2P)
      return (TransferApp.TransferP2P)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      TransferApp._TransferP2PStub stub = new TransferApp._TransferP2PStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
