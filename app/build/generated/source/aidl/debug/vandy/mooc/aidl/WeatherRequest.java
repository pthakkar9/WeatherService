/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\dev\\coursera\\android 4\\assignments\\submission - 3\\WeatherService\\app\\src\\main\\aidl\\vandy\\mooc\\aidl\\WeatherRequest.aidl
 */
package vandy.mooc.aidl;
/**
 * Interface defining the method implemented within
 * WeatherServiceAsync that provides asynchronous access to the
 * Weather Service web service.
 */
public interface WeatherRequest extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements vandy.mooc.aidl.WeatherRequest
{
private static final java.lang.String DESCRIPTOR = "vandy.mooc.aidl.WeatherRequest";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an vandy.mooc.aidl.WeatherRequest interface,
 * generating a proxy if needed.
 */
public static vandy.mooc.aidl.WeatherRequest asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof vandy.mooc.aidl.WeatherRequest))) {
return ((vandy.mooc.aidl.WeatherRequest)iin);
}
return new vandy.mooc.aidl.WeatherRequest.Stub.Proxy(obj);
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
case TRANSACTION_getCurrentWeather:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
vandy.mooc.aidl.WeatherResults _arg1;
_arg1 = vandy.mooc.aidl.WeatherResults.Stub.asInterface(data.readStrongBinder());
this.getCurrentWeather(_arg0, _arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements vandy.mooc.aidl.WeatherRequest
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
    * A one-way (non-blocking) call to the WeatherServiceAsync that
    * retrieves information about the current weather from the Weather
    * Service web service.  WeatherServiceAsync subsequently uses the
    * WeatherResults parameter to return a List of WeatherData
    * containing the results from the Weather Service web service back
    * to the WeatherActivity via the one-way sendResults() method.
    */
@Override public void getCurrentWeather(java.lang.String Weather, vandy.mooc.aidl.WeatherResults results) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(Weather);
_data.writeStrongBinder((((results!=null))?(results.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_getCurrentWeather, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_getCurrentWeather = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
    * A one-way (non-blocking) call to the WeatherServiceAsync that
    * retrieves information about the current weather from the Weather
    * Service web service.  WeatherServiceAsync subsequently uses the
    * WeatherResults parameter to return a List of WeatherData
    * containing the results from the Weather Service web service back
    * to the WeatherActivity via the one-way sendResults() method.
    */
public void getCurrentWeather(java.lang.String Weather, vandy.mooc.aidl.WeatherResults results) throws android.os.RemoteException;
}
