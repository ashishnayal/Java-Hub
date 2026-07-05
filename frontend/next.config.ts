import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  /* Disable Turbopack CSS handling since we removed tailwind/postcss */
  experimental: {},
  output: "standalone",
};

export default nextConfig;
