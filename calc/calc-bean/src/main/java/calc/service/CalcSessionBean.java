package calc.service;

import javax.ejb.Stateless;

@Stateless
public class CalcSessionBean implements Calc {

	@Override
	public int add(int a, int b) {
		return a + b;
	}
}
