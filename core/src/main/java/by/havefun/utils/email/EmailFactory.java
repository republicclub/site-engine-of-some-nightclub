package by.havefun.utils.email;

public class EmailFactory {
	
	public static EmailAccount getPromo() {
		EmailAccount emailAccount = new EmailAccount();
		emailAccount.setEMAIL("afishasend@havefun.by");
		emailAccount.setLOGIN("afishasend@havefun.by");
		emailAccount.setPASSWORD("FUUCK123TheS90");
		emailAccount.setSERVER("smtp.yandex.com");
		emailAccount.setSMTPPORT("587");
		return emailAccount;
	}
}
