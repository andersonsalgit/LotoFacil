package util;

public class MemoriaMain {

	public static void main(String[] args) {
		
		StringBuffer buffer = new StringBuffer(200);
        buffer.append("Memória máxima permitida para a JVM:");
        final long RAM_MAX = Runtime.getRuntime().maxMemory();
        final long RAM_MAX_MB = RAM_MAX / 1024 / 1024;
        buffer.append(RAM_MAX_MB);
        buffer.append(" Mb\n");

        buffer.append("Memória atualmente alocada na JVM:");
        final long RAM_TOTAL = Runtime.getRuntime().totalMemory();
        final long RAM_TOTAL_MB = RAM_TOTAL / 1024 / 1024;
        buffer.append(RAM_TOTAL_MB);
        buffer.append(" Mb\n");
        
        buffer.append("Memória atualmente livre na JVM:");
        final long RAM_FREE = Runtime.getRuntime().freeMemory();
        final long RAM_FREE_MB = RAM_FREE  / 1024 / 1024;
        buffer.append(RAM_FREE_MB);
        buffer.append(" Mb\n");
        
        buffer.append("Memória atualmente usada na JVM:");
        final long RAM_USED = RAM_TOTAL - RAM_FREE;
        final long RAM_USED_MB = (long) RAM_USED / 1024 / 1024;
        buffer.append(RAM_USED_MB);
        buffer.append(" Mb\n");
        
        buffer.append("Porcentagem de RAM utilizada na JVM: ");
        final double RAM_USED_PERCENTAGE = ((double) RAM_USED / RAM_TOTAL) * 100;
        buffer.append(RAM_USED_PERCENTAGE);
        buffer.append(" %");
        
        System.out.println(buffer.toString());

	}

}
