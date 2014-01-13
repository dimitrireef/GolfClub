package golf.club.app.model;

public class ClassementGolf {


	/*
	 * {
    "scores": [
        {
            "@id": 1000000162,
            "@version": 1,
            "@status": "active",
            "@completionDate": "2013-12-19T00:00:00",
            "@shots": 0,
            "@netResult": 0,
            "player": {
                "@id": 1000000142,
                "@version": 1,
                "@status": "active",
                "@nickName": "dim123",
                "@email": "so@ka.toi",
                "@handicap": 36,
                "friends": {
                    "friend": [

                    ]
                },
	 */



	public String id, version,status, completionDate,shots,netResult,player,events,messageRooms,paymentProfile;


	public ClassementGolf(String id1, String version1, String status1, String completionDate1, String shots1, String netResult1, String player1,String events1, String messageRooms1,String paymentProfile1 ){

		this.id=id1;
		this.version=version1;
		this.status=status1;
		this.completionDate=completionDate1;
		this.shots=shots1;
		this.netResult=netResult1;
		this.player=player1;
		this.events=events1;
		this.messageRooms=messageRooms1;
		this.paymentProfile=paymentProfile1;

	}


	public String getId() {
		return id;
	}


	public String getVersion() {
		return version;
	}


	public String getStatus() {
		return status;
	}


	public String getShots() {
		return shots;
	}


	public String getNetResult() {
		return netResult;
	}


	public String getPlayer() {
		return player;
	}


	public String getMessageRooms() {
		return messageRooms;
	}


	public String getPaymentProfile() {
		return paymentProfile;
	}






}
