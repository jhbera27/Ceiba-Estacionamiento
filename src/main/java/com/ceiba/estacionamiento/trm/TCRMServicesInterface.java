package com.ceiba.estacionamiento.trm;

public interface TCRMServicesInterface extends java.rmi.Remote 
{
    public com.ceiba.estacionamiento.trm.TcrmResponse queryTCRM(java.util.Calendar tcrmQueryAssociatedDate) throws java.rmi.RemoteException;
}
