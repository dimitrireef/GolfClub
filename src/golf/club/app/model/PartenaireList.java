package golf.club.app.model;

public class PartenaireList {

	/* "@id": 1000000110,
     "@version": 1,
     "@fromDate": "2014-01-07T00:00:00",
     "@toDate": "2014-10-07T00:00:00",
     "player": {
         "@id": 1000000050,
         "@version": 6,
         "@nickName": "eric.coquelin"
     },
     "golf": {
         "@id": 101,
         "@version": 1,
         "@name": "Golf de Bondoufle",
         "@distance": 9399.109
     }
 },

	 */

	String id, version, fromDate, toDate, player, golf;

	public PartenaireList(String id,String version,String fromDate,String toDate,String player,String golf){

		this.id=id;
		this.version=version;
		this.fromDate=fromDate;
		this.toDate=toDate;
		this.player=player;
		this.golf=golf;

	}

	public String getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public String getPlayer() {
		return player;
	}

	public String getGolf() {
		return golf;
	}




}
