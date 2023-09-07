/*
  div사이즈 동적으로 구하기
*/
const outer = document.querySelector('#outer_1');
const innerList = document.querySelector('#main_recommend_img_1');
const inners = document.querySelectorAll('#goods_list_1');
let currentIndex = 0; // 현재 슬라이드 화면 인덱스

/*
  버튼에 이벤트 등록하기
*/
const buttonLeft = document.querySelector('#leftbtn_1');
const buttonRight = document.querySelector('#rightbtn_1');

buttonLeft.addEventListener('click', () => {
  currentIndex--;
  currentIndex = currentIndex < 0 ? 0 : currentIndex;
// index값이 0보다 작아질 경우 0으로 변경
  innerList.style.marginLeft = 
  `-${innerList.clientWidth * currentIndex}px`; 
  // index만큼 margin을 주어 옆으로 밀기
});

buttonRight.addEventListener('click', () => {
  currentIndex++;
  currentIndex = currentIndex >= inners.length ? inners.length - 1 : currentIndex; 
  // index값이 inner의 총 개수보다 많아질 경우 마지막 인덱스값으로 변경
  innerList.style.marginLeft = `-${innerList.clientWidth * currentIndex}px`;
  // index만큼 margin을 주어 옆으로 밀기
});

const outer_2 = document.querySelector('#outer_2');
const innerList_2 = document.querySelector('#main_recommend_img_2');
const inners_2 = document.querySelectorAll('#goods_list_2');
let currentIndex_2 = 0; // 현재 슬라이드 화면 인덱스

/*
  버튼에 이벤트 등록하기
*/
const buttonLeft_2 = document.querySelector('#leftbtn_2');
const buttonRight_2 = document.querySelector('#rightbtn_2');

buttonLeft_2.addEventListener('click', () => {
  currentIndex_2--;
  currentIndex_2 = currentIndex_2 < 0 ? 0 : currentIndex_2;
// index값이 0보다 작아질 경우 0으로 변경
  innerList_2.style.marginLeft = 
  `-${innerList_2.clientWidth * currentIndex_2}px`; 
  // index만큼 margin을 주어 옆으로 밀기
});

buttonRight_2.addEventListener('click', () => {
  currentIndex_2++;
  currentIndex_2 = currentIndex_2 >= inners_2.length ? inners_2.length - 1 : currentIndex_2; 
  // index값이 inner의 총 개수보다 많아질 경우 마지막 인덱스값으로 변경
  innerList_2.style.marginLeft = `-${innerList_2.clientWidth * currentIndex_2}px`;
  // index만큼 margin을 주어 옆으로 밀기
});