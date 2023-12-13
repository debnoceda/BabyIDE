.data
newline: .asciiz "\n"
.text
main:
    li $t0 1

.data
x: .word 1
.text

    lw $t1, x

