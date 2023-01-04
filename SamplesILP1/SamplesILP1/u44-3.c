#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 
ILP_Object print;


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp133; 
{ 
  ILP_Object ilptmp134; 
  ILP_Object ilptmp135; 
ilptmp134 =  ILP_String2ILP("aa"); 
ilptmp135 =  ILP_String2ILP("aa"); 
ilptmp133 = ILP_Equal(ilptmp134, ilptmp135);
} 
return ILP_print(ilptmp133);
}

} 

static ILP_Object ilp_caught_program () {
  struct ILP_catcher* current_catcher = ILP_current_catcher;
  struct ILP_catcher new_catcher;

  if ( 0 == setjmp(new_catcher._jmp_buf) ) {
    ILP_establish_catcher(&new_catcher);
    return ilp_program();
  };
  return ILP_current_exception;
}

int main (int argc, char *argv[]) 
{ 
  ILP_START_GC; 
  ILP_print(ilp_caught_program()); 
  ILP_newline(); 
  return EXIT_SUCCESS; 
} 
