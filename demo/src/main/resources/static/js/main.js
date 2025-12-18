// USFK Jobs Platform - Main JavaScript

document.addEventListener('DOMContentLoaded', function() {
    console.log('USFK Jobs Platform loaded');
    
    // 폼 유효성 검사
    const forms = document.querySelectorAll('.needs-validation');
    forms.forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        });
    });
});

// 아이디 중복 확인
function checkDuplicateId(type) {
    const loginId = document.getElementById('loginId').value;
    if (!loginId) {
        alert('아이디를 입력해주세요.');
        return;
    }
    
    fetch(`/auth/check-id?loginId=${loginId}&type=${type}`)
        .then(response => response.json())
        .then(data => {
            if (data.exists) {
                alert('이미 사용 중인 아이디입니다.');
            } else {
                alert('사용 가능한 아이디입니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('중복 확인 중 오류가 발생했습니다.');
        });
}

// 이메일 인증번호 발송
function sendVerificationEmail() {
    const email = document.getElementById('email').value;
    if (!email) {
        alert('이메일을 입력해주세요.');
        return;
    }
    
    fetch('/auth/send-verification', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email: email })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('인증번호가 발송되었습니다.');
        } else {
            alert('인증번호 발송에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('인증번호 발송 중 오류가 발생했습니다.');
    });
}

// 삭제 확인
function confirmDelete(message) {
    return confirm(message || '정말 삭제하시겠습니까?');
}
