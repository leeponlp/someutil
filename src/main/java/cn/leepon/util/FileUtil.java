package cn.leepon.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @see 文件操作工具类FileUtil
 * @author leepon
 * @version 1.0
 * @since 2015-12-10
 *
 */
public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);

	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean exists(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * 获取文件绝对路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileAbsolutePath(String fileName) {
		File file = new File(fileName);
		return file.getAbsolutePath();
	}

	/**
	 * 获取文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName) {
		File file = new File(fileName);
		return file.getName();
	}

	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 * 
	 * @param fileName
	 *            文件的名
	 */
	public static void readFileByByte(String fileName) {
		File file = new File(fileName);
		InputStream in = null;
		try {
			logger.info("以字节为单位读取文件内容，一次读一个字节：");
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				System.out.write(tempbyte);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 * 
	 * @param fileName
	 *            文件的名
	 */
	public static void readFileByBytes(String fileName) {
		File file = new File(fileName);
		InputStream in = null;
		try {
			logger.info("以字节为单位读取文件内容，一次读多个字节：");
			// 一次读多个字节
			byte[] tempbytes = new byte[100];
			int byteread = 0;
			in = new FileInputStream(file);
			int available = FileUtil.showAvailableBytes(in);
			logger.info("当前字节输入流中的字节数为:" + available);
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1) {
				System.out.write(tempbytes, 0, byteread);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}

	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 *
	 * @param fileName
	 *            文件名
	 */
	public static void readFileByChar(String fileName) {
		File file = new File(fileName);
		Reader reader = null;
		try {
			logger.info("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				// 对于windows下，rn这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉r，或者屏蔽n。否则，将会多出很多空行。
				if (((char) tempchar) != 'r') {
					System.out.print((char) tempchar);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void readFileByChars(String fileName) {
		File file = new File(fileName);
		Reader reader = null;
		try {
			logger.info("以字符为单位读取文件内容，一次读多个字节：");
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(file));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉r不显示
				if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != 'r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == 'r') {
							continue;
						} else {
							System.out.print(tempchars[i]);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 *
	 * @param fileName
	 *            文件名
	 */
	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			logger.info("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 随机读取文件内容
	 *
	 * @param fileName
	 *            文件名
	 */
	public static void readFileByRandomAccess(String fileName) {
		RandomAccessFile randomFile = null;
		try {
			logger.info("随机读取一段文件内容：");
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 读文件的起始位置
			int beginIndex = (fileLength > 4) ? 4 : 0;
			// 将读文件的开始位置移到beginIndex位置。
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			// 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
			// 将一次读取的字节数赋给byteread
			while ((byteread = randomFile.read(bytes)) != -1) {
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 显示输入流中还剩的字节数
	 *
	 * @param in
	 */
	private static int showAvailableBytes(InputStream in) {
		int available = 0;
		try {
			available = in.available();
			return available;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return available;
	}

	/**
	 * A方法追加文件：使用RandomAccessFile
	 *
	 * @param fileName
	 *            文件名
	 * @param content
	 *            追加的内容
	 */
	public static void appendMethodA(String fileName, String content) {
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * B方法追加文件：使用FileWriter
	 *
	 * @param fileName
	 * @param content
	 */
	public static void appendMethodB(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件内容（使用UTF-8编码）
	 * 
	 * @param filePath
	 *            输出文件路径
	 * @return
	 * @throws Exception
	 */
	public static String readFileUTF8(String filePath) throws Exception {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		String fileContent = "";
		String temp = "";
		while ((temp = br.readLine()) != null) {
			fileContent = fileContent + temp;
		}
		br.close();
		fis.close();
		return fileContent;
	}

	/**
	 * 将文件内容写入文件（使用UTF-8编码）
	 * 
	 * @param content
	 *            文件内容
	 * @param filePath
	 *            输出文件路径
	 * @throws Exception
	 */
	public static void writeFileUTF8(String content, String filePath) throws Exception {
		createDir(filePath);
		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
		bw.write(content);
		bw.flush();
		bw.close();
		fos.close();
	}

	/**
	 * 写文件
	 * 
	 * @param outputPath
	 *            输出文件路径
	 * @param is
	 *            输入流
	 * @param isApend
	 *            是否追加
	 * @throws IOException
	 */
	public static void writeFile(InputStream is, String outputPath, boolean isApend) throws IOException {
		FileInputStream fis = (FileInputStream) is;
		createDir(outputPath);
		FileOutputStream fos = new FileOutputStream(outputPath, isApend);
		byte[] bs = new byte[1024 * 16];
		int len = -1;
		while ((len = fis.read(bs)) != -1) {
			fos.write(bs, 0, len);
		}
		fos.close();
		fis.close();
	}

	/**
	 * copy文件
	 * 
	 * @param is
	 *            输入流
	 * @param outputPath
	 *            输出文件路径
	 * @throws Exception
	 */
	public static void writeFile(InputStream is, String outputPath) throws Exception {
		InputStream bis = null;
		OutputStream bos = null;
		createDir(outputPath);
		bis = new BufferedInputStream(is);
		bos = new BufferedOutputStream(new FileOutputStream(outputPath));
		byte[] bs = new byte[1024 * 10];
		int len = -1;
		while ((len = bis.read(bs)) != -1) {
			bos.write(bs, 0, len);
		}
		bos.flush();
		bis.close();
		bos.close();
	}

	/**
	 * 写文件
	 * 
	 * @param outputPath
	 *            输出文件路径
	 * @param inPath
	 *            输入文件路径
	 * @throws IOException
	 */
	public static void writeFile(String inPath, String outputPath, boolean isApend) throws IOException {
		File file = new File(inPath);
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			writeFile(fis, outputPath, isApend);
		} else {
			logger.info("源文件不存在，文件复制失败!");
		}
	}

	/**
	 * 将字符串写到文件内
	 * 
	 * @param outputPath
	 *            输出文件路径
	 * @param msg
	 *            字符串
	 * @param isApend
	 *            是否追加
	 * @throws IOException
	 */
	public static void writeContent(String msg, String outputPath, boolean isApend) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath, isApend));
		bw.write(msg);
		bw.flush();
		bw.close();
	}

	/**
	 * 删除文件夹下的所有内容,包括本文件夹
	 * 
	 * @param path
	 *            删除文件路径
	 * @throws IOException
	 */
	public static void delFileOrDerectory(String path) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					File subFile = files[i];
					delFileOrDerectory(subFile.getAbsolutePath());
				}
				file.delete();
			} else {
				file.delete();
			}
		}
	}

	/**
	 * 如果欲写入的文件所在目录不存在，需先创建
	 * 
	 * @param outputPath
	 *            输出文件路径
	 */
	public static void createDir(String outputPath) {
		File outputFile = new File(outputPath);
		File outputDir = outputFile.getParentFile();
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}
	}

	/**
	 * 作用：创建文件 注意：如果不存在的，创建。如果已经存在的，则删除后再创建，所以创建的时候需要谨慎 时间：2015年2月25日上午11:29:06
	 * 
	 * @throws IOException
	 */
	public static void createFile(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
	}

	/**
	 * 作用：可以返回用户输入的信息 注意：不足之处在于不支持中文输入，有待进一步改进。
	 * 
	 * @throws IOException
	 */
	public static String getInputMessage() throws IOException {
		System.out.println("请输入您的命令∶");
		byte buffer[] = new byte[1024];
		int count = 0;
		count = System.in.read(buffer);
		char[] ch = new char[count - 2];// 最后两位为结束符，删去不要
		for (int i = 0; i < count - 2; i++) {
			ch[i] = (char) buffer[i];
		}
		String str = new String(ch);
		return str;
	}

	/**
	 * 作用：使用通道，快速复制文件
	 */
	public void fastCopy(File s, File t) throws IOException {
		FileInputStream fi = new FileInputStream(s);
		FileOutputStream fo = new FileOutputStream(t);
		FileChannel in = fi.getChannel();// 得到对应的文件通道
		FileChannel out = fo.getChannel();// 得到对应的文件通道
		in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		fi.close();
		in.close();
		fo.close();
		out.close();

	}

	/**
	 * 作用：利用PrintStream写文件
	 * 
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public void write1(String path, String content) throws UnsupportedEncodingException, IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file, true);
		StringBuffer sb = new StringBuffer(content);
		out.write(sb.toString().getBytes("utf-8"));
		out.close();
	}

	/**
	 * 作用：写文件
	 */
	public static void write2(String path, String str) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.write(str);
		if (bw != null) {
			bw.close();

		}
	}

	/**
	 * 作用：文件复制
	 * 
	 * @throws IOException
	 */
	public static void copy1(String src, String dest) throws IOException {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		bis = new BufferedInputStream(new FileInputStream(src));
		// 对目标做如下处理
		// 先检查目录是否存在，不存在的在创建目录
		int rr = dest.lastIndexOf("/");
		String mulu = dest.substring(0, rr);
		File mu = new File(mulu);
		if (!mu.exists()) {
			mu.mkdir();
		}
		// 再检查文件是否存在，如果不存在的，则创建
		File file = new File(dest);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 以追加的方式复制
		bos = new BufferedOutputStream(new FileOutputStream(file, true));
		byte[] buff = new byte[1024];
		for (int len = 0; len != -1;) {
			len = bis.read(buff);
			bos.write(buff);
		}
		bos.flush();
		if (bis != null) {
			bis.close();
		}
		if (bos != null) {
			bos.close();
		}

	}

	/**
	 * 作用：文件复制
	 */
	public static void copy2(String src, String dest) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(src));
		// 对目标做如下处理
		// 先检查目录是否存在，不存在的在创建目录
		int rr = dest.lastIndexOf("/");
		String mulu = dest.substring(0, rr);
		File mu = new File(mulu);
		if (!mu.exists()) {
			mu.mkdir();
		}
		// 再检查文件是否存在，如果不存在的，则创建
		File file = new File(dest);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 以追加的方式复制
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		char[] buff = new char[1024];
		for (int len = 0; len != -1;) {
			len = br.read(buff);
			bw.write(buff);
		}
		bw.flush();
		if (br != null) {
			br.close();
		}
		if (bw != null) {
			bw.close();
		}
	}

	/**
	 * 作用：读文件
	 * 
	 * @throws IOException
	 */
	public static String read(String path) throws IOException {
		File file = new File(path);
		BufferedInputStream bis = null;
		StringBuffer result = null;
		if (!file.exists()) {
			return "ERROR";
		} else {
			bis = new BufferedInputStream(new FileInputStream(file));
			result = new StringBuffer();
			byte[] buff = new byte[1024];
			for (int len = 0; len != -1;) {
				len = bis.read(buff);
				result.append(new String(buff));
				buff = new byte[1024];
			}

			if (bis != null) {
				bis.close();
			}
		}
		return result.toString();
	}

	/**
	 * 作用：读取全部并放在一个字符串中
	 */
	public static String readAll(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		char[] buff = new char[1024];
		StringBuffer result = new StringBuffer();
		for (int len = 0; len != -1;) {
			len = br.read(buff);
			result.append(new String(buff));
		}
		if (br != null) {
			br.close();
		}
		return result.toString();
	}

	/**
	 * 作用：读取全部并放在一个集合中
	 */
	public static List<String> readList(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		List<String> result = new LinkedList<String>();
		for (;;) {
			String s = br.readLine();
			if (null == s || ("").equals(s)) {
				break;
			}
			result.add(s);
		}
		if (br != null) {
			br.close();
		}
		return result;
	}

	/**
	 * 作用：读取一行
	 */
	public static String readLine(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String result = br.readLine();
		if (br != null) {
			br.close();
		}
		return result;

	}
     
  

	
	public static void main(String[] args) {
		//String fileName = "C:/temp/newTemp.txt";
		// ReadFromFile.readFileByBytes(fileName);
		// /ReadFromFile.readFileByChars(fileName);
		// ReadFromFile.readFileByLines(fileName);
		// ReadFromFile.readFileByRandomAccess(fileName);
	}

	public static void main1(String[] args) {
		//String fileName = "C:/temp/newTemp.txt";
		//String content = "new append!";
		// 按方法A追加文件
		// AppendToFile.appendMethodA(fileName, content);
		// AppendToFile.appendMethodA(fileName, "append end. n");
		// 显示文件内容
		// ReadFromFile.readFileByLines(fileName);
		// 按方法B追加文件
		// AppendToFile.appendMethodB(fileName, content);
		// AppendToFile.appendMethodB(fileName, "append end. n");
		// 显示文件内容
		// ReadFromFile.readFileByLines(fileName);
	}
}
