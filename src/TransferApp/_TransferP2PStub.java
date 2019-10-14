package TransferApp;


/**
* TransferApp/_TransferP2PStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Transfer.idl
* Segunda-feira, 14 de Outubro de 2019 18h44min21s GFT
*/

public class _TransferP2PStub extends org.omg.CORBA.portable.ObjectImpl implements TransferApp.TransferP2P
{

  public byte[] downloadFile (String fileName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("downloadFile", true);
                $out.write_string (fileName);
                $in = _invoke ($out);
                byte $result[] = TransferApp.BytesHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return downloadFile (fileName        );
            } finally {
                _releaseReply ($in);
            }
  } // downloadFile

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:TransferApp/TransferP2P:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _TransferP2PStub
