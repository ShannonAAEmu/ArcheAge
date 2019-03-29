package info.aaemu.converter.dat.entity;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class GetData {

	private Map<String, DoodadNpc> map;

	private String fileName;
	private byte[] byteDatFile;
	private String datByteString;
	private Path jsonString;

	private String stringId;
	private int id;
	private String x;
	private String y;
	private String z;

	public void convert(Path datFile) throws IOException {
		createJson(datFile);
		map = new HashMap<String, DoodadNpc>();
		byteDatFile = Files.readAllBytes(datFile);
		datByteString = new BigInteger(byteDatFile).toString(16);
		int startPos = 0;
		while (startPos < datByteString.length()) {
			stringId = datByteString.substring(startPos, startPos + 4);
			stringId = stringId.substring(2, 4) + stringId.substring(0, 2);
			x = datByteString.substring(startPos + 8, startPos + 16);
			y = datByteString.substring(startPos + 16, startPos + 24);
			z = datByteString.substring(startPos + 24, startPos + 32);
			startPos = startPos + 32;
			id = convertId(stringId);
			x = convertCoordinates(x);
			y = convertCoordinates(y);
			z = convertCoordinates(z);
			map.put(String.valueOf(map.size()), new DoodadNpc(id, x, y, z));
		}
		jsonString = Paths.get(datFile.getParent().toString() + "\\" + fileName).toAbsolutePath();
		Files.write(jsonString, new JSONObject(map).toString().getBytes());
	}

	private void createJson(Path datFile) {
		fileName = datFile.getFileName().toString();
		fileName = fileName.replace(".dat", ".json");
	}

	private int convertId(String stringId) {
		return Integer.parseInt(stringId, 16);
	}

	private String convertCoordinates(String coordinate) {
		coordinate = coordinate.substring(6, 8) + coordinate.substring(4, 6) 
			+ coordinate.substring(2, 4) + coordinate.substring(0, 2);
		Long longId = Long.parseLong(coordinate, 16);
		Float floatId = Float.intBitsToFloat(longId.intValue());
		return new DecimalFormat("##.##########").format(floatId);
	}

}
