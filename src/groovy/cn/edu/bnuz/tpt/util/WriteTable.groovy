package cn.edu.bnuz.tpt.util
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

class WriteTable {
	public HWPFDocument replaceDoc(String srcPath, Map<String, String> map) {
		try {
// ��ȡwordģ��
			FileInputStream fis = new FileInputStream(new File(srcPath));
			HWPFDocument doc = new HWPFDocument(fis);
			
// ��ȡword�ı�����
			Range bodyRange = doc.getRange();
// �滻�ı�����
			for (Map.Entry<String, String> entry : map.entrySet()) {
				bodyRange.replaceText("@{" + entry.getKey() + "}",
						entry.getValue());
					
			}
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	def write(String templateFile,String filename,Map<String,String> map){
		HWPFDocument doc = replaceDoc(templateFile, map);
        FileOutputStream out = new FileOutputStream(filename);
        doc.write(out);
        out.close();
	}
	public HWPFDocument getWordFile(String templateFile){
		FileInputStream fis = new FileInputStream(new File(templateFile));
		HWPFDocument doc = new HWPFDocument(fis);
		return doc
	}
}
