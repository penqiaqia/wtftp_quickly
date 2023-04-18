package com.ftp.qanlong.utils;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Util {
	// 获取buferedImge
	public static BufferedImage getImg(String resource) throws IOException {
		BufferedImage bufImg = ImageIO.read(new File(resource));
		return bufImg;
	}

	public static Image setImageSize(Image img, int width, int height) {
//		BufferedImage img = null;
//		try {
//			img = ImageIO.read(new File("src/main/resources/code.jpg"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Image imge = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
	}

	// 读取地图
	public static void writeObject(String path, Object object) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			DataOutputStream dos = new DataOutputStream(fos);
			ObjectOutputStream oos = new ObjectOutputStream(dos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Object> readObject(String path) {
		List<Object> maps = null;
		try {
			FileInputStream in = new FileInputStream(path);
			DataInputStream datain = new DataInputStream(in);
			ObjectInputStream objin = new ObjectInputStream(datain);
			maps = (ArrayList) objin.readObject();
			objin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return maps;
	}

	// 切割图片并返回图像数组
	public static BufferedImage[][] getImageArr(BufferedImage img, int length) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage[][] imgs = new BufferedImage[height / length][width / length];
		for (int i = 0; i < imgs.length; i++) {
			for (int j = 0; j < imgs[i].length; j++) {
				imgs[i][j] = img.getSubimage(j * length, i * length, length, length);
			}
		}
		return imgs;
	}

	/*
	 * 获取屏幕宽和高
	 */
	public static Rectangle getwindowDimension() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return ge.getMaximumWindowBounds();
	}

	/*
	 * 获取任务管理器高度
	 */
	public static int getButtomHight(Component c) {
		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(c.getGraphicsConfiguration());
		System.out.println(insets.bottom);
		return insets.bottom;
	}

	/*
	 * 生成二维码文件
	 */
	public static void generateCode(String url, String path, String codeName, int width, int height)
			throws WriterException, IOException {
		final int BLACK = 0xFF000CE0;
		final int WHITE = 0xFFF6FF3F;
//		int width = 400;
//		int height = 400;
		// 二维码图片格式
		String format = "jpg";
		// 设置编码，防止中文乱码
		Hashtable<EncodeHintType, Object> ht = new Hashtable<EncodeHintType, Object>();
		ht.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 设置二维码参数(编码内容，编码类型，图片宽度，图片高度,格式)
		BitMatrix bitMatrix = null;
		bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, ht);
		// 生成二维码(定义二维码输出服务器路径)
		File outputFile = new File(path);
		if (!outputFile.exists()) {
			// 创建文件夹
			outputFile.mkdir();
		}

		int b_width = bitMatrix.getWidth();
		int b_height = bitMatrix.getHeight();
		// 建立图像缓冲器
		BufferedImage image = new BufferedImage(b_width, b_height, BufferedImage.TYPE_3BYTE_BGR);
		for (int x = 0; x < b_width; x++) {
			for (int y = 0; y < b_height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		// 生成二维码
		ImageIO.write(image, format, new File(path + "/" + codeName + "." + format));

	}

	/*
	 * 生成二维码图片对象
	 */
	public static Image generateCodeImg(String url, int width, int height) throws WriterException, IOException {
		final int BLACK = 0xFF000CE0;
		final int WHITE = 0xFFF6FF3F;
//		int width = 400;
//		int height = 400;
		// 二维码图片格式
		// 设置编码，防止中文乱码
		Hashtable<EncodeHintType, Object> ht = new Hashtable<EncodeHintType, Object>();
		ht.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 设置二维码参数(编码内容，编码类型，图片宽度，图片高度,格式)
		BitMatrix bitMatrix = null;
		bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, ht);
		int b_width = bitMatrix.getWidth();
		int b_height = bitMatrix.getHeight();
		// 建立图像缓冲器
		BufferedImage image = new BufferedImage(b_width, b_height, BufferedImage.TYPE_3BYTE_BGR);
		for (int x = 0; x < b_width; x++) {
			for (int y = 0; y < b_height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		// 生成二维码
		return image;

	}

	/*
	 * 连接sqlite
	 */
	public static Connection getSqlite3Conn(String path) {
		Connection conn = null;
		String url = "jdbc:sqlite:" + path;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;

	}

	public static Connection getMySqlConn() {
		Connection conn = null;
		try {

			Class.forName(Util.getProperty("driver"));
			conn = DriverManager.getConnection(Util.getProperty("url"), Util.getProperty("username"),
					Util.getProperty("password"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static String getProperty(String key) throws FileNotFoundException, IOException {
		Properties pro = new Properties();
		pro.load(new FileInputStream("src/main/resources/properties.properties"));
		return pro.getProperty(key);
	}

	public static void closeConn(Connection c) {
		if (c != null)
			try {
				c.close();
				c = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static void closePS(PreparedStatement p) {
		if (p != null)
			try {
				p.close();
				p = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static void closeRS(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	// md5加密
	public static String codingMD5(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("md5加密失败");
		}
		md.reset();
		byte[] arr = md.digest(str.getBytes());
		String temp = new BigInteger(1, arr).toString(16);
		for (int i = 0; i < 32 - temp.length(); i++) {
			temp = "a" + temp;
		}
		return temp.toUpperCase();
	}

	/**
	 * 读取文件内容并压缩，既支持文件也支持文件夹
	 *
	 * @param filePath 文件路径
	 */
	public static void compressFileToZip(String filePath, String zipFilePath) {
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
			// 递归的压缩文件夹和文件
			doCompress("", filePath, zos);
			// 必须
			zos.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void doCompress(String parentFilePath, String filePath, ZipOutputStream zos) {
		File sourceFile = new File(filePath);
		if (!sourceFile.exists()) {
			return;
		}
		String zipEntryName = parentFilePath + "/" + sourceFile.getName();
		if (parentFilePath.isEmpty()) {
			zipEntryName = sourceFile.getName();
		}
		if (sourceFile.isDirectory()) {
			File[] childFiles = sourceFile.listFiles();
			if (Objects.isNull(childFiles)) {
				return;
			}
			for (File childFile : childFiles) {
				doCompress(zipEntryName, childFile.getAbsolutePath(), zos);
			}
		} else {
			int len = -1;
			byte[] buf = new byte[1024 * 1024];
			try (InputStream input = new BufferedInputStream(new FileInputStream(sourceFile))) {
				zos.putNextEntry(new ZipEntry(zipEntryName));
				while ((len = input.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 文件解压缩，支持文件和文件夹的解压
	 *
	 * @param zipFilePath  压缩包路径
	 * @param destFilePath 解压路径
	 */
	public static void decompressFromZip(String zipFilePath, String destFilePath) {
		File file = new File(zipFilePath);
		try (ZipFile zipFile = new ZipFile(file); ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
			ZipEntry zipEntry = null;
			while ((zipEntry = zis.getNextEntry()) != null) {
				String fileName = destFilePath + "/" + zipEntry.getName();
				File entryFile = new File(fileName);
				if (zipEntry.isDirectory()) {
					// 创建文件夹
					entryFile.mkdir();
				} else {
					// 创建文件之前必须保证父文件夹存在
					if (!entryFile.getParentFile().exists()) {
						entryFile.getParentFile().mkdirs();
					}
					// 创建文件
					entryFile.createNewFile();
				}
				try (InputStream input = zipFile.getInputStream(zipEntry);
						OutputStream output = new FileOutputStream(entryFile)) {
					int len = -1;
					byte[] buf = new byte[1024];
					while ((len = input.read(buf)) != -1) {
						output.write(buf, 0, len);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void copy(File sourceFile, File targerFile) throws IOException {
		FileInputStream in = new FileInputStream(sourceFile);
		FileOutputStream out = new FileOutputStream(targerFile);
		byte[] buf = new byte[1024 * 2048];
		int a;
		while ((a = in.read(buf)) != -1) {
			out.write(buf, 0, a);
		}
		out.flush();
		out.close();
		in.close();

	}

}
