package kiosk

import java.time.LocalDateTime

//모든 화면들은 선택할 메뉴들을 보여주고 값을 받아 동작하기 때문에, 인터페이스를 선언한 다음 모두 각자 클래스에서 showMenu()함수를 구현할거예요.
interface Show {
    fun showMenu()
}

//모든 메뉴들은 Menu클래스의 생성자를 통해서 이런 프로퍼티들을 가질거예요.

data class Menu(val name: String, val price: Int?, val info: String) { //키오스크메인화면 메뉴들은 price가 없어서 ?로 nullable표시를 해뒀어요.
   override fun toString(): String {
        return "$name   ${price}원   $info"
    } //showMenu()로 리스트를 불러와서 println()에서 객체들을 호출하면 주소값이 나오기 때문에, Menu들의 각 프로퍼티 정보들이 잘 나올 수 있도록 Any로부터 toString을 override 했어요.
    //data class를 사용하면 자동으로 toString()를 생성하기 때문에 별도로 오버라이드할 필요가 없지만, 원하는 방식의 문자열 표현이 있어서 오버라이드 했어요.
}


class KioskMenu: Show { //Show 인터페이스를 구현한다는 뜻이에요
    private val kioskMenu: List<Menu> = listOf( //Menu클래스의 객체들로 이루어진 List입니다.
        Menu("Burgers              ", null, "[앵거스 비프 통살을 다져만든 버거]"),
        Menu("Frozen Custard       ", null, "[매장에서 신선하게 만드는 아이스크림]"),
        Menu("Drinks               ", null, "[매장에서 직접 만드는 음료]"),
        Menu("장바구니               ", null, "[장바구니에 담긴 메뉴를 볼 수 이써요]")
    )

    override fun showMenu() { //Show 인터페이스의 showMenu()함수를 구현해요.
        println("\n       [ ===== ShakeShack 키오스크 메인화면 ===== ]\n") //위 아래로 한 줄 씩 띄우기!
        kioskMenu.forEachIndexed { index: Int, category: Menu ->
            // kioskMenu리스트 안에 있는 각 요소들의 index와, 요소 자체(category)를 받아서 출력하는 람다예요. 반환값이 Unit이라서 값을 반환하는 건 안 돼요.
            println("${index + 1}.  ${category.name} ${category.info}") //Kiosk 메인화면에서 카테고리 부를 때 나오는 형식을 지정했어요. (price가 null인 건 안나오게 메뉴이름과 설명만 불러와요.)
        }
    }
}

class BurgersMenu : Show {
    private val burger: List<Menu> = listOf(
        Menu("Shack Burger         ", 6900, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"),
        Menu("Smoke Shack          ", 8900, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"),
        Menu("Cheese burger        ", 6900, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거")
    )
    override fun showMenu() {
        while (true) {
            println("\n       [ ===== BURGERS MENU ===== ]\n")
            burger.forEachIndexed {index: Int, burgermenu: Menu -> println("${index + 1}.  ${burgermenu}")}
            // burger은 Menu클래스 형식의 객체니까 Menu클래스에서 override한 toString()에서 정한 형태로 출력돼요.
            println("0.  이전 화면\n\n        원하시는 메뉴를 선택해주세요. ")
            var userInput: Int = readln().toInt() //사용자가 입력하는 숫자를 받아서 userInput에 넣을거예요.
            if (userInput == 0) {  //사용자가 0을 입력하면 해당 루프를 벗어나서, 다시 main의 루프로 돌아가요.
                break
            } else if (userInput in 1..burger.size) {
                var selected: Menu = burger[userInput - 1] //1~메뉴수만큼의 숫자를 입력하면, 해당 인덱스의 메뉴가 selected라는 변수에 저장돼요.
                println("        ${burger[userInput - 1].name} 제품이 장바구니에 추가되었습니다. ")
                Cart.addItem(selected) //Cart클래스의 addItem함수에 selected를 인자로 전달해요. Cart클래스는 object키워드로 정의해서, 단일객체기 때문에 이렇게 호출할 수 있어요.
            } else {
                println("잘못된 선택입니다.")
            }
            println("       추가하실 메뉴가 있나요?")
        }
    }
}

class ShakeMenu : Show {
    private val shake: List<Menu> = listOf(
        Menu("Vanilla Shake        ", 5900, "바닐라 쉐이크"),
        Menu("Chocolate Shake      ", 5900, "초콜릿 쉐이크"),
        Menu("Coffee Shake         ", 5900, "커피맛 쉐이크")
    )

    override fun showMenu() { //같은 로직을 반복하게 되는데, 이 showMenu들을 하나로 만들고 각 리스트 이름마다 다르게 동작되도록 할 수 있을 것 같은데 시간이 모자라요ㅠㅠ
        while (true) {
            println()
            println("       [ ===== Frozen Custard MENU ===== ]")
            println()
            shake.forEachIndexed { index: Int, shakemenu: Menu -> println("${index + 1}.  ${shakemenu}") }
            println("0.  이전 화면\n\n        원하시는 메뉴를 선택해주세요. ")
            var userInput: Int = readln().toInt()

            if (userInput == 0) {
                break
            } else if (userInput in 1..shake.size) {
                var selected: Menu = shake[userInput - 1]
                println("        ${shake[userInput - 1].name} 제품이 장바구니에 추가되었습니다. ")
                Cart.addItem(selected)
            } else {
                println("잘못된 선택입니다.")
            }
            println("       추가하실 메뉴가 있나요?")
        }
    }
}

class DrinksMenu : Show {
    val drink: List<Menu> = listOf(
        Menu("ShackMade Lemonade   ", 3900, "레모네이드"),
        Menu("Fresh Brewed Iced Tea", 3400, "아이스 티"),
        Menu("Fifty Fifty          ", 3500, "레모네이드와 아이스티 50대 50으로 상큼~~")
    )

    override fun showMenu() {
        while (true) {
            println()
            println("       [ ===== Drinks MENU ===== ]")
            println()
            drink.forEachIndexed { index: Int, drinkmenu: Menu -> println("${index + 1}.  ${drinkmenu}") }
            println("0.  이전 화면\n\n        원하시는 메뉴를 선택해주세요. ")
            var userInput: Int = readln().toInt()

            if (userInput == 0) {
                break
            } else if (userInput in 1..drink.size) {
                var selected: Menu = drink[userInput - 1]
                println("        ${drink[userInput - 1].name} 제품이 장바구니에 추가되었습니다. ")
                Cart.addItem(selected)
            } else {
                println("잘못된 선택입니다.")
            }
            println("       추가하실 메뉴가 있나요?")
        }
    }
}
//프로그램 전체에서 하나의 인스턴스만을 갖도록 object키워드를 붙여서 싱글톤 객체를 선언했어요. 어느 클래스에서 장바구니를 호출하든 하나의 상태를 유지하기 위함이에요.
object Cart : Show {
    val cartItem = mutableListOf<Menu>()
    val yesOrNo: List<Menu> = listOf(
        Menu("예", null, "주문하겠습니다. "),
        Menu("아니오", null, "취소하겠습니다. ")
    )

    fun addItem(selected: Menu) {
        cartItem.add(selected)
    }

    fun sumTotal(): Int {
        var totalPrice = 0
        for (menu in cartItem) {
            totalPrice += menu.price ?: 0 //price가 null이 아니라고 표시하라고 에러가 떠서 엘비스 연산자를 사용했어요.
        }
        return totalPrice
    }

    override fun showMenu() {
        println()
        println("       [ ===== 장바구니 목록 ===== ]")
        if(cartItem.isEmpty()){
            println("           장바구니가 비어있어요.")
        } else {cartItem.forEachIndexed { index: Int, menu: Menu ->
                println("${index + 1}. ${menu.name} ${menu.price}원")
            }
            println("   총 가격은 ${sumTotal()}원 입니다. \n")
            println("   위와 같이 주문하시겠습니까?\n")
            yesOrNo.forEachIndexed{index: Int, menu: Menu ->
                print("${index + 1}. ${menu.name}, ${menu.info}     ")
            }
            println()
            var userInput = readln().toInt()
            if (userInput == 1) {println("       주문이 완료되었습니다. ")
            println(LocalDateTime.now())} //주문 완료하면 날짜와 시간이 나와요
            else {println("     주문이 취소되었습니다. ")}
        }
    }
}


fun main() {
    val burgers = BurgersMenu()
    val shakes = ShakeMenu()
    val drinks = DrinksMenu()
    val kiosk = KioskMenu()
    while (true) {
        kiosk.showMenu()
        println("0.  주문 종료\n\n        원하시는 메뉴를 선택해주세요. ")
        var userInput: Int = readln().toInt()
        when (userInput) {
            1 -> burgers.showMenu()
            2 -> shakes.showMenu()
            3 -> drinks.showMenu()
            4 -> {Cart.showMenu() //Cart는 클래스명으로 불러도 돼요
                return }
            0 -> return
            else -> println("잘못된 선택입니다. 다시 선택해주세요.")
        }
    }
}