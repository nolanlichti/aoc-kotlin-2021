fun main() {
    debug.enabled = false

    fun part1(input: List<String>): Int {
        val packet = input.getPacket()
        packet.print()
        return packet.getAllPackets().sumOf { it.version }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day16_test")
//    val test1 = part1(testInput)
//    val expected1 = 31
//    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day16")
    println("Part 1: ${part1(input)}")

//    val test2 = part2(testInput)
//    val expected2 = 0
//    check(test2 == expected2) { "Expected $expected2, was $test2" }
//    println("Part 2: ${part2(input)}")
}

typealias Packet = String

fun List<String>.getPacket(): Packet =
    this[0].toList().map { it.digitToInt(16) }.joinToString("") { it.toString(2).padStart(4, '0') }

val Packet.versionString get() = this.substring(0, 3)
val Packet.version get() = this.versionString.toInt(2)
val Packet.typeString get() = this.substring(3, 6)
val Packet.type get() = this.typeString.toInt(2)
val Packet.lengthTypeString get() = if (this.type == 4) null else this[6]
val Packet.lengthType get() = this.lengthTypeString?.digitToInt()
val Packet.subPacketLengthString get() = this.lengthType?.let { if (it == 0) 22 else 18 }?.let { this.substring(7, it) }
val Packet.subPacketLength get() = this.subPacketLengthString?.toInt(2)
val Packet.subPacketString: String?
    get() = this.lengthType?.let {
        if (it == 0) this.substring(
            22, 22 + this.subPacketLength!!
        ) else this.substring(18)
    }

val Packet.subPackets
    get(): List<Packet> {
        if (this.type == 4) {
            return listOf()
        }
        var counter = 0
        var currentPacket = this.subPacketString!!
        val packets = mutableListOf<Packet>()
        while (counter < this.subPacketLength!!) {
            val subPacketBody = currentPacket.literalString
                ?: currentPacket.lengthTypeString!!.plus(currentPacket.subPacketLengthString!!)
                    .plus(currentPacket.subPacketString!!)
            val subPacket = currentPacket.versionString.plus(currentPacket.typeString).plus(subPacketBody)
            packets.add(subPacket)
            counter += if (this.lengthType == 0) subPacket.length else 1
            if (counter < this.subPacketLength!!) {
                currentPacket = currentPacket.substring(subPacket.length)
            }
        }
        return packets
    }

fun Packet.getAllPackets(): List<Packet> = mutableListOf(this) + this.subPackets.flatMap { it.getAllPackets() }

val Packet.literalString: String?
    get() {
        if (this.type != 4) {
            return null
        }
        val chunks = this.substring(6).chunked(5)
        return chunks.subList(0, chunks.indexOfFirst { it.startsWith("0") } + 1).joinToString("")
    }

val Packet.literal get() = this.literalString?.chunked(5)?.joinToString("") { it.substring(1, 5) }?.toInt(2)

fun Packet.print() {
    println("Packet: $this")
    println("Packet size: ${this.length}")
    println("Version: ${this.version}")
    println("Type: ${this.type}")
    println("Length Type: ${this.lengthType}")
    println("Subpacket Length: ${this.subPacketLength}")
    println("Literal: ${this.literal}")
    println("Subpackets: ${this.subPackets.size}")
    println("All packets: ${this.getAllPackets().size}")
    this.subPackets.forEach { it.print() }
}