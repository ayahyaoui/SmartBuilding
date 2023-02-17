#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp97; 
ilptmp97 = ILP_Integer2ILP(33); 

  {
    ILP_Object u1 = ilptmp97;
{ 
  ILP_Object ilptmp98; 
ilptmp98 =  ILP_String2ILP("foobar"); 
{ 
  ILP_Object ilptmp99; 
  ILP_Object ilptmp100; 
ilptmp99 = u1; 
ilptmp100 = ILP_Integer2ILP(22); 
ilptmp98 = ILP_Plus(ilptmp99, ilptmp100);
} 
return ilptmp98; 
} 

  }
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
