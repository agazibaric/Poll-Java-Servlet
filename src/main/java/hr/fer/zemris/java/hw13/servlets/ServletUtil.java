package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ServletUtil contains helper function and classes for web servlets.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
public class ServletUtil {

	/**
	 * Class contains band informations:
	 * 	<li> band ID </li>
	 *  <li> band name </li>
	 *  <li> band song's link </li>
	 *  <li> number of votes for the band </li>
	 *  <p>
	 *  
	 * @author Ante Gazibaric
	 *
	 */
	public static class BandInfo implements Comparable<BandInfo> {

		/**
		 * Band ID number.
		 */
		private String id;
		/**
		 * Name of the band.
		 */
		private String name;
		/**
		 * Link of the band's representative song.
		 */
		private String link;
		/**
		 * Number of votes for the band.
		 */
		private Integer numberOfVotes;
		/**
		 * Default value for band info.
		 */
		private static final String DEFAULT_VALUE = "undefined";
		/**
		 * Comparator for comparing bands by number of votes.
		 */
		public static final Comparator<BandInfo> BY_NUM_OF_VOTES = 
				(b1, b2) -> b2.numberOfVotes.compareTo(b1.numberOfVotes);

		/**
		 * Constructor that creates new {@link BandInfo} object.<br>
		 * Number of votes for the band is set to zero.
		 * 
		 * @param id   ID of the band
		 * @param name Name of the band
		 * @param link Link of the band's representative song
		 */
		public BandInfo(String id, String name, String link) {
			this(id, name, link, "0");
		}
		
		/**
		 * Constructor that creates new {@link BandInfo} object.
		 * 
		 * @param id  		    ID of the band
		 * @param name		    Name of the band
		 * @param link 			Link of the band's representative song
		 * @param numberOfVotes Number of votes for the band
		 */
		public BandInfo(String id, String name, String link, String numberOfVotes) {
			this.id = id;
			this.name = name;
			this.link = link;
			this.numberOfVotes = getNumberOfVotesFromString(numberOfVotes);
		}

		/**
		 * Default constructor that creates new {@link BandInfo} object. <p>
		 * All the values for the band are set to default value "undefined" <br>
		 * except for the number of votes that is set to zero.
		 */
		public BandInfo() {
			this(DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE);
		}

		/**
		 * Method returns band's ID.
		 * 
		 * @return band's ID
		 */
		public String getId() {
			return id;
		}

		/**
		 * Method returns band's name.
		 * 
		 * @return band's name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Method returns link of the band's representative song.
		 * 
		 * @return link of the band's representative song
		 */
		public String getLink() {
			return link;
		}
		
		/**
		 * Method returns number of votes for the band.
		 * 
		 * @return number of votes for the band
		 */
		public Integer getNumberOfVotes() {
			return numberOfVotes;
		}
		
		/**
		 * Method sets number of votes for the band.
		 * 
		 * @param numberOfVotes number of votes for the band
		 */
		public void setNumberOfVotes(String numberOfVotes) {
			this.numberOfVotes = getNumberOfVotesFromString(numberOfVotes);
		}
		
		/**
		 * Method adds one vote for the band.
		 */
		public void addVote() {
			numberOfVotes++;
		}
		
		/**
		 * Method parses given {@code votes} into Integer number of votes and returns it. <br>
		 * If it's not parsable, method returns zero.
		 * 
		 * @param votes string that represents number of votes for the band
		 * @return      integer that represents number of votes for the band
		 */
		private Integer getNumberOfVotesFromString(String votes) {
			try {
				return Integer.parseInt(votes);
			} catch (NumberFormatException ex) {
				return 0;
			}
		}

		@Override
		public int compareTo(BandInfo band) {
			return this.id.compareTo(band.id);
		}
		
	}
	
	/**
	 * Method returns list of {@link BandInfo} objects that has number of votes set to zero.
	 * 
	 * @param lines represents information about band without number of votes for the band
	 * @return      list of {@link BandInfo} objects
	 */
	public static List<BandInfo> getBandInfoList(List<String> lines) {
		return lines.stream()
				.map(l -> getBandInfoFrom(l))
				.collect(Collectors.toList());
	}

	/**
	 * Helper method that creates {@link BandInfo} object form given {@code line}. <br>
	 * Given line must contain all information of the band except for the number of votes.
	 * 
	 * @param line represents information about band
	 * @return     {@link BandInfo} object
	 */
	private static BandInfo getBandInfoFrom(String line) {
		String[] lineParts = line.split("\t");
		try {
			return new BandInfo(lineParts[0], lineParts[1], lineParts[2]);
		} catch (ArrayIndexOutOfBoundsException ex) {
			return new BandInfo();
		}
	}

	/**
	 * Method returns list of {@link BandInfo} objects that contains all the information about the band.<br>
	 * Given {@code lines} must contain band's ID and number of votes for the band.<br>
	 * Given {@code sourcePath} must point to the file that contains definition for every band.
	 * 
	 * @param lines        lines that contain band's ID and number of votes for the band
	 * @param sourcePath   path of the file that contains band's definitions
	 * @return             list of {@link BandInfo} objects.
	 * @throws IOException if reading from sourcePath fails
	 */
	public static List<BandInfo> getVotingList(List<String> lines, Path sourcePath) throws IOException {
		List<BandInfo> bandInfo = getBandInfoList(Files.readAllLines(sourcePath));
		setVotingNumbers(lines, bandInfo);
		return bandInfo;
	}
	
	/**
	 * Method sets number of votes for given {@code bands}.
	 * Given {@code lines} must contain band's ID and number of votes for the band.
	 * 
	 * @param lines lines that contain band's ID and number of votes for the band
	 * @param bands list of band whose number of votes is updated
	 */
	private static void setVotingNumbers(List<String> lines, List<BandInfo> bands) {
		for (String line : lines) {
			String[] lineParts = line.split("\t");
			String bandID = lineParts[0].trim();
			String numberOfVotes = lineParts[1].trim();
			setBandVotingNumber(bandID, numberOfVotes, bands);
		}
	}
	
	/**
	 * Helper method that sets number of votes for the band in given list {@code bands}
	 * that has given {@code bandID}.
	 * 
	 * @param bandID        ID of the band whose votes are updated
	 * @param numberOfVotes number of votes for the band 
	 * @param bands         list of band which is updated
	 */
	private static void setBandVotingNumber(String bandID, String numberOfVotes, List<BandInfo> bands) {
		for (BandInfo band : bands) {
			if (band.getId().equals(bandID)) {
				band.setNumberOfVotes(numberOfVotes);
				return;
			}
		}
	}
	
	/**
	 * Method creates file that contains band's ID and number of votes for that band. <br>
	 * Given {@code sourcePath} must point to the definition of the bands. <br>
	 * New file is created at the given {@code destinationPath}.
	 * 
	 * @param sourcePath      path of the file that contains definition of bands
	 * @param destinationPath path of the newly created file
	 * @throws IOException    if reading or writing to files fails
	 */
	public synchronized static void createFile(Path sourcePath, Path destinationPath) throws IOException {
		List<BandInfo> bandInfoList = ServletUtil.getBandInfoList(Files.readAllLines(sourcePath));
		List<String> votingList = bandInfoList.stream()
						.map(b -> b.getId() + "\t" + "0")
						.collect(Collectors.toList());
		Files.write(destinationPath, votingList);
	}
	
	/**
	 * Method updates content of the given {@code filePath}
	 * according to information that given {@code votingList} contains.
	 * 
	 * @param votingList   list {@link BandInfo} objects.
	 * @param filePath     path of the file that is updated
	 * @throws IOException if writing to the file fails
	 */
	public synchronized static void updateFile(List<BandInfo> votingList, Path filePath) throws IOException {
		List<String> lines = votingList.stream()
					.map(v -> v.getId() + "\t" + v.getNumberOfVotes())
					.collect(Collectors.toList());
		Files.write(filePath, lines, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	/**
	 * Method returns list of {@link BandInfo} objects that have greatest number of votes.<br>
	 * Minimum number of the bands in returned list is one, assuming that given list is not empty.
	 * 
	 * @param bands list of all bands
	 * @return      list of {@link BandInfo} objects that have greatest number of votes
	 */
	public static List<BandInfo> getWinnerBands(List<BandInfo> bands) {
		int max = 0;
		for (BandInfo band : bands) {
			if (band.getNumberOfVotes() > max) {
				max = band.getNumberOfVotes();
			}
		}
		final int finalMax = max;
		return bands.stream()
				.filter(b -> b.getNumberOfVotes() == finalMax)
				.collect(Collectors.toList());
	}

}
