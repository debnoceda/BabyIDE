.data
newline: .asciiz "\n"
.text
main:
    li $t0 1

.data
x: .word 1
.text

    lw $t1, x


.data
y: .asciiz "Miko"
.text

    li $t2 100

.data
z: .word 100
.text

    lw $t3, z


.data
a: .asciiz "hehe"
.text

    li $t4 10

.data
b: .word 10
.text

    lw $t5, b

