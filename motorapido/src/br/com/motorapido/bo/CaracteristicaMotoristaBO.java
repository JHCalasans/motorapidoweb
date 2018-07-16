package br.com.motorapido.bo;

public class CaracteristicaMotoristaBO extends MotoRapidoBO {

	private static CaracteristicaMotoristaBO instance;

	private CaracteristicaMotoristaBO() {

	}

	public static CaracteristicaMotoristaBO getInstance() {
		if (instance == null)
			instance = new CaracteristicaMotoristaBO();

		return instance;
	}

	

}
