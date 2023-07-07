package cl.security.mdd.dao;

import cl.security.model.Params;

public abstract class Repair {
	
	public Params p;
	public String reparo;
	
	Repair() {
	}
	
	public abstract Repair build(Params p, String reparo);
	
	public abstract boolean queryUpdateRepair();

}
