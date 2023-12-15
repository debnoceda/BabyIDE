.data
newline: .asciiz "\n"
.text
main:


.data
ans: .word 0
.text

    sw EmptyStack, ans


    li $v0, 1
    lw $a0, ans
    syscall


    li $v0, 4
    la $a0, newline
    syscall

