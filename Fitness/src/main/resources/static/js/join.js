var lang = location.pathname.substr(0, 3);

function validatePassword() {
	if (document.querySelector('#password').value !== document.querySelector('#confirm-password').value) {
		confirmPassword.setCustomValidity('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
	} else {
		confirmPassword.setCustomValidity('');
	}
}
	// 쿼리 셀렉터는 css 선택자를 그대로 가져와서 쓴다.
function activateButton(btnId) {
	// 모든 버튼에서 active 클래스 제거
	document.querySelectorAll('div[id^="btn_"]').forEach(btn => btn.classList.remove('active'));
	// 선택한 버튼에 active 클래스 추가
	document.getElementById(btnId).classList.add('active');
	// input(gender) 값 변경
	document.getElementById('gender').value = btnId.split('_')[1];
}

// 익명 함수
$(() => {
	const password = document.querySelector('#password');
	const confirmPassword = document.querySelector('#confirm-password');
	password.addEventListener('input', validatePassword);
	confirmPassword.addEventListener('input', validatePassword);

	const yearSelect = document.getElementById("year");
	const monthSelect = document.getElementById("month");
	const daySelect = document.getElementById("day");
	const currentYear = new Date().getFullYear();

	// 년도 옵션 생성
	for (let i = currentYear - 100; i <= currentYear; i++) {
		const option = document.createElement("option");
		option.value = i;
		option.textContent = i;
		if (i == currentYear) option.selected = true;
		yearSelect.appendChild(option);
	}

	// 월 옵션 생성
	for (let i = 1; i <= 12; i++) {
		const option = document.createElement("option");
		option.value = i;
		option.textContent = i;
		monthSelect.appendChild(option);
	}

	// 일 옵션 생성
	function updateDays() {
		const year = yearSelect.value;
		const month = monthSelect.value;
		const daysInMonth = new Date(year, month, 0).getDate();

		daySelect.innerHTML = "";

		for (let i = 1; i <= daysInMonth; i++) {
			const option = document.createElement("option");
			option.value = i;
			option.textContent = i;
			daySelect.appendChild(option);
		}
	}

	yearSelect.addEventListener("change", updateDays);
	monthSelect.addEventListener("change", updateDays);
	updateDays();

		
	$("#check-email").on("click", function () {
		const email = $("#email").val();
		if (!email) {
			$("#email-result").text("이메일을 입력해주세요.");
			return;
		}

		$.ajax({
			type: "POST",
			url: lang + "/check-email",
			data: {"email": email},
			success: function (response) {
				if (response.duplicate) {
					$("#email-result").text("이미 사용 중인 이메일입니다.");
					checkMail = false;
				} else {
					$("#email-result").text("사용 가능한 이메일입니다.");
					checkMail = true;
				}
				
			},
			error: function () {
				
				$("#email-result").text("오류가 발생했습니다. 다시 시도해주세요.");
				checkMail = false;
				
			}
		});
	});
	
		
	$("#check-member_id").on("click", function () {
		const member_id = $("#mem_id").val();
		if (!member_id) {
			$("#member_id-result").text("사용자 이름을 입력해주세요.");
			return;
		}

		$.ajax({
			type: "POST",
			url: lang + "/check-member_id",
			data: {"mem_id": member_id},
			success: function (response) {
				if (response.duplicate) {
					$("#member_id-result").text("이미 사용 중인 사용자 이름입니다.");
					checkMemberId = false;
				} else {
					$("#member_id-result").text("사용 가능한 사용자 이름입니다.");
					checkMemberId = true;
				}
				
			},
			error: function () {
				$("#member_id-result").text("오류가 발생했습니다. 다시 시도해주세요.");
				checkMemberId = false;
			}
		});
	});
});
  

let checkMail = false;
function resetCheckEmail() {
	checkMail = false;
	  $("#email-result").text("");
}

let checkMemberId = false;
function resetCheckMemberId() {
	  checkMemberId = false;
	  $("#member_id-result").text("");
}

function validatePhoneNumber() {
  let phone = document.getElementById("phone").value;
  let regex = /^[0-9]{10,12}$/;
  if (!regex.test(phone)) {
    alert("전화번호는 10자리 이상, 12자리 이하의 숫자로 입력해주세요.");
    return false;
  }
  return true;
}


function checkForm(){
	if(checkMail && checkMemberId && validatePhoneNumber) {
		return true;
	} else {
		alert("회원가입 오류");
		return false;
	}
}