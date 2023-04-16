//配合key03
package c.mid09;

public class Member {
	private int id;
	private String account,realname,birthday;
	public Member(int id,String account,String realname,String birthday) {
		this.id=id;this.account=account;this.realname=realname;this.birthday=birthday;
	}
	
	//下面這些是source按出來的
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}
