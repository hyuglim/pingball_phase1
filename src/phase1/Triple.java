package phase1;

public class Triple<One,Two, Three> {

	private final One one;
	private final Two two;
	private final Three three;

	public Triple(One one, Two two, Three three) {
		this.one = one;
		this.two = two;
		this.three = three;
	}

	public One getOne() { return one; }
	public Two getTwo() { return two; }
	public Three getThree() {return three;}

	@Override
	public int hashCode() { return one.hashCode() ^ two.hashCode() ^ three.hashCode(); }

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Triple)) return false;
		Triple triple = (Triple) o;
		return this.one.equals(triple.getOne()) &&
				this.two.equals(triple.getTwo()) && 
				this.three.equals(triple.getThree());
	}

}
