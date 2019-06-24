package com.starwars.resistence.model;

import java.util.Date;

public interface NegociacaoView {
	Long getId();
	Date getAlteracao_Dt();
	Date getCriacao_Dt();
	Long getComprador_Id();
	Long getVendedor_Id();
	String getComprador_Nome();
	String getVendedor_Nome();
}
