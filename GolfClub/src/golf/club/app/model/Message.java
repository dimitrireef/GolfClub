package golf.club.app.model;

public class Message {
	/*
	 * {
    "rooms": [
        {
            "@id": 1000000250,
            "@subject": "test",
            "@startDate": "2013-12-23T10:55:48",
            "@noOfParticipants": 2
        },
        {
            "@id": 1000000257,
            "@subject": "szdvvs",
            "@startDate": "2013-12-23T12:01:08",
            "@noOfParticipants": 2
        },
        {
            "@id": 1000000281,
            "@subject": "sacasdcasdc",
            "@startDate": "2013-12-24T06:30:52",
            "@noOfParticipants": 2
        }
    ]
}
	 */

	public  String id,subject,startDate,noOfParticipants;

	public Message(  String  id1,String subject1,String startDate1,String noOfParticipants1){

		this.id=id1;
		this.subject=subject1;
		this.startDate=startDate1;
		this.noOfParticipants=noOfParticipants1;

	}

	public String getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getNoOfParticipants() {
		return noOfParticipants;
	}




}
