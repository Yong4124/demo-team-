// 첨부파일 확장자 확인
function fileCheck(file) {
	var ext = file.substr(file.lastIndexOf('.')+1).toUpperCase();
	var permit = false;
	var permitArr = new Array(  'PDF', 'DOC',  'DOCX', 'XLS',  'LSX', 
								'JPG', 'PEG',  'GIF',  'TXT',  'HWP',  
								'PNG', 'BMP',  'PPT',  'PTX',  'ZIP' );
	
	for (var p=0; p<permitArr.length; p++) {
		if (ext == permitArr[p]) {
			permit = true;
			break;
		}
	}

	if (!permit) {
		alert('업로드가 제한된 첨부파일 형식입니다. ( '+ ext.toLowerCase() + ' )'); 
		return false;
	}

	return true;
}

// 이미지 첨부파일 확장자 확인
function imgFileCheck(file) {
	var ext = file.substr(file.lastIndexOf('.')+1).toUpperCase();
	var permit = false;
	var permitArr = new Array('JPG', 'EPG', 'GIF', 'PNG', 'BMP');
	
	for (var p=0; p<permitArr.length; p++) {
		if (ext == permitArr[p]) {
			permit = true;
			break;
		}
	}

	if (!permit) {
		/* alert('업로드가 제한된 첨부파일 형식입니다.('+ ext.toLowerCase() + ')'); */
		return false;
	}

	return true;
}

//-----------------------------------------------------------
// 파일 다운로드
//-----------------------------------------------------------
// html에 hidden frame 사용 : 다운로드시 팝업 안닫힘.
// <iframe id="hiddenFrame" style="display:none;"></iframe>
//-----------------------------------------------------------
function popupFileDown( file, filePath )
{
	var len = file.length;
	var fileType = file.substring(len - 3).toUpperCase();
	var httpType = file.substring(0, 4).toUpperCase();
	var filePath;

	// 허용 확장자
//	const allowedTypes = ["PDF", "HWP", "PPT", "TXT", "DOC", "OCX", "PTX", "XLS", "LSX", "JPG", "PNG", "BMP", "GIF", "TML"];
	const allowedTypes = ["PDF", "DOC", "OCX", "XLS", "LSX", "JPG", "PEG", "GIF", "TXT", "HWP", "PNG", "BMP", "PPT", "PTX", "ZIP" ];

	if (allowedTypes.includes(fileType)) {
		if (httpType != "HTTP") {
			filePath = "https://www.usfkjobs.com/File/Job/";
			file = filePath + file;
		}
		// window.open 대신 iframe으로 다운로드
		document.getElementById("hiddenFrame").src = file;
	} else {
		alert("Only PDF format is allowed.");
	}
	return true;
}

//-----------------------------------------------------------
// 팝업창이 아닌경우 사용 hidden frame 필요 없음.
//-----------------------------------------------------------
function fileDown( file, filePath )
{
	var len, fileType, httpType;
	
	len = file.length 
	fileType = file.substring( (len-3), len);
	fileType = fileType.toUpperCase();
	
	httpType =  file.substring( 0, 4);
	httpType = httpType.toUpperCase();

	if ( fileType == "PDF" || fileType == "DOC" || fileType == "OCX" || fileType == "XLS" || 
		 fileType == "LSX" || fileType == "JPG" || fileType == "PEG" || fileType == "GIF" || 
		 fileType == "TXT" || fileType == "HWP" || fileType == "PNG" || fileType == "BMP" || 
		 fileType == "PPT" || fileType == "PTX" || fileType == "PTX" || fileType == "ZIP") {
		if( httpType != "HTTP" ) {
			// filePath = "/File/Job/";
			file = filePath + file;
		}
		window.open( file, "openWin" );
	}
	return true;
}