.data
newline: .asciiz "\n"
.text
main:
    li $t0 2

.data
x: .word 2
.text

    lw $t1, x

    li $t2 3

.data
y: .word 3
.text

    lw $t3, y

