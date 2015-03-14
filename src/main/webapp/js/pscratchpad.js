// attributes for key
var keyAttributes = {
	size : 128,
	iter : 5000
};

// initialize data
function doInit() {
	var password = localStorage.getItem("password");
	if (password != null) {
		document.getElementById("password").value = password;
	}
}

// encrypt data
function doEncrypt() {
	var password = document.getElementById("password").value;
	localStorage.setItem("password", password);
	var edittextArea = document.getElementById("edittextarea");
	var plaintext = edittextArea.value;

	var salt = forge.random.getBytesSync(16);
	var iv = forge.random.getBytesSync(16);

	var key = forge.pkcs5.pbkdf2(password, salt, keyAttributes.iter,
			keyAttributes.size / 8);

	var cipher = forge.cipher.createCipher('AES-CBC', key);
	cipher.start({
		iv : iv
	});
	cipher.update(forge.util.createBuffer(plaintext));
	cipher.finish();
	var encrypted = cipher.output;
	var ciphertext64 = forge.util.encode64(encrypted.getBytes());

	var encryptedJSON = {
		key : {
			size : keyAttributes.size,
			iter : keyAttributes.iter,
			salt : forge.util.bytesToHex(salt)
		},
		iv : forge.util.bytesToHex(iv),
		ciphertext : ciphertext64
	}

	edittextArea.value = JSON.stringify(encryptedJSON);
}

// decrypt data
function doDecrypt() {
	var password = document.getElementById("password").value;
	var edittextArea = document.getElementById("edittextarea");

	var encryptedJSON = edittextArea.value;
	var encryptedData = JSON.parse(encryptedJSON);
	var saltHex = encryptedData.key.salt;
	var salt = forge.util.hexToBytes(saltHex);

	var key = forge.pkcs5.pbkdf2(password, salt, encryptedData.key.iter,
			encryptedData.key.size / 8);

	var iv = forge.util.hexToBytes(encryptedData.iv);

	var ciphertext = forge.util.decode64(encryptedData.ciphertext);
	var encrypted = forge.util.createBuffer(ciphertext);

	var decipher = forge.cipher.createDecipher('AES-CBC', key);
	decipher.start({
		iv : iv
	});
	decipher.update(encrypted);
	decipher.finish();

	var plaintextData = decipher.output.toString('utf8');

	edittextArea.value = plaintextData;
}

$(document).ready(function() {
	doInit();
	$("#encrypt").click(doEncrypt);
	$("#decrypt").click(doDecrypt);
});
