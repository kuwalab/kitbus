module.exports = (grunt) ->
  grunt.initConfig
    pkg: grunt.file.readJSON('package.json')
    dirs:
      root: '.'
      scss: '<%= dirs.root %>/scss'
      css: '<%= dirs.root %>/css'

    sass:
      app:
        options:
          style: 'compressed'
        files: '<%= dirs.css %>/main.css': '<%= dirs.scss %>/main.scss'

    watch:
      scripts:
        files: ['**/*.sass']
        tasks: ['default']
        options:
          interrupt: true

    clean:
      all: [
        'node_modules'
      ]

  grunt.loadNpmTasks('grunt-contrib-clean')
  grunt.loadNpmTasks('grunt-contrib-sass')
  grunt.loadNpmTasks('grunt-contrib-watch')

  grunt.registerTask('default', ['sass'])
