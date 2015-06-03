/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\dev\\coursera\\android 4\\assignments\\submission - 3\\WeatherService\\app\\src\\main\\aidl\\vandy\\mooc\\aidl\\AcronymResults.aidl
 */
package vandy.mooc.aidl;
/**
 * Interface defining the method that receives callbacks from the
 * AcronymServiceAsync.
 */
public interface AcronymResults extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements vandy.mooc.aidl.AcronymResults
{
private static final java.lang.String DESCRIPTOR = "vandy.mooc.aidl.AcronymResults";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an vandy.mooc.aidl.AcronymResults interface,
 * generating a proxy if needed.
 */
public static vandy.mooc.aidl.AcronymResults asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof vandy.mooc.aidl.AcronymResults))) {
return ((vandy.mooc.aidl.AcronymResults)iin);
}
return new vandy.mooc.aidl.AcronymResults.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_sendResults:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<vandy.mooc.aidl.AcronymData> _arg0;
_arg0 = data.createTypedArrayList(vandy.mooc.aidl.AcronymData.CREATOR);
this.sendResults(_arg0);
return true;
}
case TRANSACTION_sendError:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.sendError(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements vandy.mooc.aidl.AcronymResults
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * This one-way (non-blocking) method allows AcyronymServiceAsync
     * to return the List of AcronymData results associated with a
     * one-way AcronymRequest.callAcronymRequest() call.
     */
@Override public void sendResults(java.util.List<vandy.mooc.aidl.AcronymData> results) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(results);
mRemote.transact(Stub.TRANSACTION_sendResults, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
/**
     * This one-way (non-blocking) method allows AcyronymServiceAsync
     * to return an error String if the Service fails for some reason.
     */
@Override public void sendError(java.lang.String reason) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(reason);
mRemote.transact(Stub.TRANSACTION_sendError, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_sendResults = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_sendError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * This one-way (non-blocking) method allows AcyronymServiceAsync
     * to return the List of AcronymData results associated with a
     * one-way AcronymRequest.callAcronymRequest() call.
     */
public void sendResults(java.util.List<vandy.mooc.aidl.AcronymData> results) throws android.os.RemoteException;
/**
     * This one-way (non-blocking) method allows AcyronymServiceAsync
     * to return an error String if the Service fails for some reason.
     */
public void sendError(java.lang.String reason) throws android.os.RemoteException;
}
