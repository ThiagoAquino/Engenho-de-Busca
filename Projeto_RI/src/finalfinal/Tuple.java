package finalfinal;

public class Tuple {

	private Integer docId;
	private double termFrequency;

	public Tuple(Integer docId, Integer termFrequency) {
		this.docId = docId;
		this.termFrequency = termFrequency;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public double getTermFrequency() {
		return termFrequency;
	}

	public void setTermFrequency(double frequencyValue) {
		this.termFrequency = frequencyValue;
	}

}
