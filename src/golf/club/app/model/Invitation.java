package golf.club.app.model;

public class Invitation {

	/* {"invitations":[{"@id":1000001451,"@version":1,
	 * "@sentDate":"2013-12-24T12:23:06.123","guest":{"@id":1000000350,"@version":11,"@nickName":"samsung","@handicap":0}}]}
	 */

	public String id, version, sentDate,guest;

	public Invitation (String id1,String version1,String sentDate1,String guest1){

		this.id=id1;
		this.version=version1;
		this.sentDate=sentDate1;
		this.guest=guest1;
	}

	public String getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public String getSendDate() {
		return sentDate;
	}

	public String getGuest() {
		return guest;
	}




}
