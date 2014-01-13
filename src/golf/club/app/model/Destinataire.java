package golf.club.app.model;

public class Destinataire {



	String id;
	String version;
	String nickName;

	public int  position;	
	public boolean ischeckedflag;

	public Destinataire(String id, String version, String nickName){


		this.id=id;
		this.version=version;
		this.nickName=nickName;

	}

	public String getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public String getNickName() {
		return nickName;
	}




	Destinataire(int name, boolean flag) {

		position = name;	   
		ischeckedflag = flag;
	}




}
